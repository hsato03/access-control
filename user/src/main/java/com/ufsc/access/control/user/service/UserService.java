package com.ufsc.access.control.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufsc.access.control.user.model.User;
import com.ufsc.access.control.user.model.dto.CreditDTO;
import com.ufsc.access.control.user.model.dto.UserDTO;
import com.ufsc.access.control.user.model.dto.UserHasToPayResponse;
import com.ufsc.access.control.user.model.enums.Category;
import com.ufsc.access.control.user.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UsuarioRepository repository;

    @Value("${credit.url.endpoint}")
    String creditEndpoint;

    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public User save(UserDTO user) {
        User newUser = new User(user);
        newUser = repository.save(newUser);

        createUserCredit(newUser);

        return newUser;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with UUID %s not found", id)));
    }

    @Transactional
    public User update(UserDTO user, UUID id) {
        User userToUpdate = findById(id);
        userToUpdate.updateFromDto(user);
        return repository.save(userToUpdate);
    }

    public void delete(UUID id) {
        User userToDelete = findById(id);
        repository.delete(userToDelete);
    }

    public void createUserCredit(User user) {
        try {
            CreditDTO credit = new CreditDTO(user.getId(), 0);
            String creditJson = objectMapper.writeValueAsString(credit);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(creditEndpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(creditJson))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InvalidParameterException(String.format("Unable to create user credit due to %s.", e));
        }
    }

    public UserHasToPayResponse hasToPay(UUID id) {
        User user = findById(id);
        boolean result = Category.STUDENT.equals(user.getCategory()) || Category.VISITOR.equals(user.getCategory());
        return new UserHasToPayResponse(result);
    }
}
