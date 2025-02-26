package com.trabajo1.ApiNombres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trabajo1.ApiNombres.models.Names;
import com.trabajo1.ApiNombres.repositories.NamesRepositories;

@RestController
public class BdController {

    @Autowired
    private NamesRepositories namesRepositories;

    @PostMapping("/api/sendNames")
    public String save(@RequestBody Names name) {
        return namesRepositories.save(name).getName();
    }


    @GetMapping("/api/getNames")
    public List<String> getNames(){
        System.out.println(namesRepositories.findAllNames());
        return namesRepositories.findAllNames();
    }

    //Nueva funcionalidad eliminar 
    @DeleteMapping("/api/deleteName/{name}")
    public ResponseEntity<String> deleteName(@PathVariable String name) {
    try {
        namesRepositories.deleteByName(name);
        return ResponseEntity.ok("Nombre eliminado correctamente: " + name);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al eliminar el nombre: " + name);
    }
}
    
}
