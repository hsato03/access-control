package com.ufsc.access.control.access.controller;

import com.ufsc.access.control.access.model.dto.AccessDTO;
import com.ufsc.access.control.access.model.dto.AccessEntryResponse;
import com.ufsc.access.control.access.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    AccessService service;

    @PostMapping("/entry")
    public ResponseEntity<AccessEntryResponse> entry(@RequestBody AccessDTO access, UriComponentsBuilder uriBuilder) {
        AccessEntryResponse accessEntry = service.entry(access);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(accessEntry.id()).toUri();

        return ResponseEntity.created(uri).body(accessEntry);
    }

    @PostMapping("/out")
    public void out() {

    }
}
