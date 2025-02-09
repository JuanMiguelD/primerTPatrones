package com.trabajo1.ApiNombres.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trabajo1.ApiNombres.models.Names;

public interface NamesRepositories extends JpaRepository<Names,Long> {
    
    @Query("SELECT u.name FROM Names u")
    List<String> findAllNames();
}
