package com.ufsc.access.control.access.service;

import com.ufsc.access.control.access.model.Access;
import com.ufsc.access.control.access.model.dto.CreditValueDTO;
import com.ufsc.access.control.access.model.dto.ParkingCapacityDTO;
import com.ufsc.access.control.access.utils.NetUtils;
import com.ufsc.access.control.access.model.dto.AccessDTO;
import com.ufsc.access.control.access.model.dto.AccessEntryResponse;
import com.ufsc.access.control.access.repository.AccessRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
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

    @Value("${gate.url.endpoint}")
    String gateEndpoint;

    @Value("${user.url.endpoint}")
    String userEndpoint;

    public AccessEntryResponse entry(AccessDTO access) {
        boolean accessEntryAlreadyExists = repository.findByUserIdAndParkingIdAndExitDateNull(
                access.userId(), access.parkingId()).isPresent();

        if (accessEntryAlreadyExists) {
            throw new RuntimeException(String.format("User %s is already using a parking spot in parking %s.", access.userId(), access.parkingId()));
        }

        decrementParkingCapacity(access.parkingId());

        Access accessToSave = new Access(access);
        accessToSave = repository.save(accessToSave);

        openGate();

        return new AccessEntryResponse(accessToSave.getId(), accessToSave.getUserId(),
                accessToSave.getParkingId(), accessToSave.getEntryDate());
    }

    public Access exit(AccessDTO access) {
        UUID userId = access.userId();
        UUID parkingId = access.parkingId();
        Access accessExit = repository.findByUserIdAndParkingIdAndExitDateNull(userId, parkingId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find a entry register that matches" +
                        " %s user UUID and %s parking UUID.", userId, parkingId)));

        if (userHasToPay(userId)){
            decrementUserCredits(access.userId());
        }

        incrementParkingCapacity(access.parkingId());

        accessExit.setExitDate(LocalDateTime.now());
        accessExit = repository.save(accessExit);

        openGate();

        return accessExit;
    }

    public void openGate() {
        String openGateEndpoint = String.format("%s/open", gateEndpoint);
        NetUtils.createGetRequest(openGateEndpoint);
    }

    public void decrementParkingCapacity(UUID parkingId) {
        String parkingIdEndpoint = String.format("%s/%s", parkingEndpoint, parkingId);
        Integer parkingCapacity = getParkingCapacity(parkingId);

        if (parkingCapacity - 1 < 0) {
            throw new RuntimeException(String.format("Parking with UUID %s does not have enough capacity.", parkingId));
        }

        parkingCapacity--;
        NetUtils.createPutRequest(parkingIdEndpoint, new ParkingCapacityDTO(parkingCapacity));
    }

    public void incrementParkingCapacity(UUID parkingId) {
        String parkingIdEndpoint = String.format("%s/%s", parkingEndpoint, parkingId);
        Integer parkingCapacity = getParkingCapacity(parkingId);

        parkingCapacity++;
        NetUtils.createPutRequest(parkingIdEndpoint, new ParkingCapacityDTO(parkingCapacity));
    }

    public void decrementUserCredits(UUID creditId) {
        String creditIdEndpoint = String.format("%s/%s", creditEndpoint, creditId);

        HttpResponse<String> creditResponse = NetUtils.createGetRequest(creditIdEndpoint);
        if (creditResponse.statusCode() != 200) {
            throw new EntityNotFoundException(String.format("Could not find a credit with UUID %s.", creditId));
        }

        Map<String, Object> creditResponseBody = NetUtils.deserializeJson(creditResponse.body());
        Integer userCredits = (Integer) creditResponseBody.get("value");

        checkIfUserHasEnoughCredits(userCredits);

        userCredits -= 15;
        NetUtils.createPutRequest(creditIdEndpoint, new CreditValueDTO(userCredits));
    }

    public void checkIfUserHasEnoughCredits(int value) {
        if (value - 15 < 0) {
            throw new RuntimeException("User does not have enough credits.");
        }
    }

    public Integer getParkingCapacity(UUID parkingId) {
        String parkingIdEndpoint = String.format("%s/%s", parkingEndpoint, parkingId);
        HttpResponse<String> response = NetUtils.createGetRequest(parkingIdEndpoint);

        if (response.statusCode() != 200) {
            throw new EntityNotFoundException(String.format("Could not find a parking with UUID %s.", parkingId));
        }

        Map<String, Object> responseBody = NetUtils.deserializeJson(response.body());
        return (Integer) responseBody.get("capacity");
    }

    public boolean userHasToPay(UUID userId) {
        String userHasToPayEndpoint = String.format("%s/%s/hasToPay", userEndpoint, userId);
        HttpResponse<String> response = NetUtils.createGetRequest(userHasToPayEndpoint);

        if (response.statusCode() != 200) {
            throw new EntityNotFoundException(String.format("Could not find a user with UUID %s.", userId));
        }

        Map<String, Object> responseBody = NetUtils.deserializeJson(response.body());

        return (boolean) responseBody.get("result");
    }
}
