package com.ufsc.controle.acesso.credito.controller;

import com.ufsc.controle.acesso.credito.model.Credit;
import com.ufsc.controle.acesso.credito.model.CreditDTO;
import com.ufsc.controle.acesso.credito.service.CreditService;
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
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    CreditService service;

    @GetMapping
    public Page<Credit> findAll(@PageableDefault(size = 20, sort = {"value"}) Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    public Credit findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Credit> save(@RequestBody Credit credit, UriComponentsBuilder uriBuilder) {
        Credit savedCredit = service.save(credit);
        URI uri = uriBuilder.path("/credit/{id}").buildAndExpand(savedCredit.getId()).toUri();
        return ResponseEntity.created(uri).body(savedCredit);
    }

    @PutMapping("/{id}")
    public Credit update(@PathVariable UUID id, @RequestBody CreditDTO credit) {
        return service.update(id, credit);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
