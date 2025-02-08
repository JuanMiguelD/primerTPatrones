package com.trabajo1.ApiNombres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trabajo1.ApiNombres.models.Names;
import com.trabajo1.ApiNombres.repositories.NamesRepositories;

@RestController
public class BdController {

    @Autowired
    private NamesRepositories namesRepositories;

    @PostMapping("/guardar")
    public ResponseEntity<Names> guardar(@RequestParam String namein){
        Names name = new Names(namein);
        return ResponseEntity.ok().body(namesRepositories.save(name));
        
       
    }
}
