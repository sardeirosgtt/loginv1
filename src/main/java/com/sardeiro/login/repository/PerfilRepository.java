package com.sardeiro.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sardeiro.login.model.Perfil;



public interface PerfilRepository extends JpaRepository<Perfil,Long> {
    
}
