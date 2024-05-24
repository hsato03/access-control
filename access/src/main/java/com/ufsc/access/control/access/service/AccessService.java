package com.ufsc.access.control.access.service;

import com.ufsc.access.control.access.model.Access;
import com.ufsc.access.control.access.model.dto.ParkingCapacityDTO;
import com.ufsc.access.control.access.utils.NetUtils;
import com.ufsc.access.control.access.model.dto.AccessDTO;
import com.ufsc.access.control.access.model.dto.AccessEntryResponse;
import com.ufsc.access.control.access.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Map;
import java.util.UUID;

@Service
public class AccessService {

    @Autowired
    AccessRepository repository;

    @Value("${credit.url.endpoint}")
    String creditEndpoint;

    @Value("${parking.url.endpoint}")
    String parkingEndpoint;

    public AccessEntryResponse entry(AccessDTO access) {
        UUID parkingId = access.parkingId();
        String parkingIdEndpoint = parkingEndpoint + "/" + parkingId;
        HttpResponse<String> response = NetUtils.createGetRequest(parkingIdEndpoint);

        if (response.statusCode() != 200) {
            throw new RuntimeException(String.format("Could not find a parking with UUID %s.", parkingId));
        }

        Map<String, Object> responseBody = NetUtils.deserializeJson(response.body());
        Integer parkingCapacity = (Integer) responseBody.get("capacity");

        if (parkingCapacity - 1 < 0) {
            throw new RuntimeException(String.format("Parking with UUID %s does not have enough capacity.", parkingId));
        }

        parkingCapacity--;
        NetUtils.createPutRequest(parkingIdEndpoint, new ParkingCapacityDTO(parkingCapacity));

        Access accessToSave = new Access(access);
        accessToSave = repository.save(accessToSave);

        return new AccessEntryResponse(accessToSave.getId(), accessToSave.getUserId(),
                accessToSave.getParkingId(), accessToSave.getEntryDate());
    }
}
