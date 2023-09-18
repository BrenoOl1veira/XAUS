package com.XAUS.DTOS;

import java.util.Date;

public record UserRequestDTO(String name, String cpf, String email, Date birthDate, String password) {

}
