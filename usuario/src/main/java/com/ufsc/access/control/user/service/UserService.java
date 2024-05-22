package com.ufsc.access.control.user.service;

import com.ufsc.access.control.user.model.User;
import com.ufsc.access.control.user.model.dto.UserDTO;
import com.ufsc.access.control.user.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UsuarioRepository repository;

    @Transactional
    public User save(UserDTO user) {
        User newUser = new User(user);
        return repository.save(newUser);
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
        userToUpdate.setName(user.name());
        userToUpdate.setCpf(user.cpf());
        userToUpdate.setCategory(user.category());
        return repository.save(userToUpdate);
    }

    public void delete(UUID id) {
        User userToDelete = findById(id);
        repository.delete(userToDelete);
    }
}
