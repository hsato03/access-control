package com.ufsc.access.control.user.controller;

import com.ufsc.access.control.user.model.dto.UserDTO;
import com.ufsc.access.control.user.service.UserService;
import com.ufsc.access.control.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public Page<User> findAll(@PageableDefault(size = 20, sort = {"name"}) Pageable page) {
        return service.findAll(page);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder) {
        User savedUser = service.save(user);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable UUID id, @RequestBody UserDTO user) {
        return service.update(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
