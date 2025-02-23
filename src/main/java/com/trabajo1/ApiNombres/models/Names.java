package com.trabajo1.ApiNombres.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "names")
public class Names {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Getter
    private long id;

    @Getter @Setter
    private String name;

    public Names(String name){
        this.name = name;
        
    }
}
