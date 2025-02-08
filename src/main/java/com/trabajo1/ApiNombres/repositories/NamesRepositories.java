package com.trabajo1.ApiNombres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trabajo1.ApiNombres.models.Names;

public interface NamesRepositories extends JpaRepository<Names,Long> {
    
}
