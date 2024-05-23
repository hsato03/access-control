package com.ufsc.controle.acesso.cancela.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gate")
public class GateController {

    @GetMapping
    public void open() {
        System.out.println("Gate opened");
    }
}
