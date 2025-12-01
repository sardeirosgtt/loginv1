package com.sardeiro.login.dto;

import com.sardeiro.login.model.Usuario;

public record LoginResponseDTO(String token, Usuario usuario) {

}
