package com.sardeiro.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String nomeLegivel;
    
    private String modulo;

    public Funcionalidade(String nome, String nomeLegivel, String modulo) {
        this.nome = nome;
        this.nomeLegivel = nomeLegivel;
        this.modulo = modulo;
    }

}
