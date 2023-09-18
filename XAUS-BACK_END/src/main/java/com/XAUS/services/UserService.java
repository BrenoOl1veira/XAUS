package com.XAUS.services;

import com.XAUS.DTOS.LoginRequestDTO;
import com.XAUS.DTOS.UserRequestDTO;
import com.XAUS.DTOS.UserResponseDTO;
import com.XAUS.Exceptions.NotFoundException;
import com.XAUS.Models.User;
import com.XAUS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAll() {
        //Metodo stream() pega tudo que vem do repositorio e coloca em um (funil) para mapearmos
        return repository.findAll().stream().map(UserResponseDTO::new).toList();

    }


    public User saveNewUser(@RequestBody UserRequestDTO data) {

        User alreadyAddedEmail = repository.findByEmail(data.email());
        User alreadyAddedCPF = repository.findByCPF(data.cpf());

        if(alreadyAddedEmail != null){
            throw new NotFoundException("Email ja cadastrado", HttpStatus.NOT_FOUND);
        }

        if(alreadyAddedCPF != null){
            throw new NotFoundException("CPF ja cadastrado", HttpStatus.NOT_FOUND);
        }


        String hashedPassword = passwordEncoder.encode(data.password());

        User userData = new User(data);
        userData.setPassword(hashedPassword);

        return this.repository.save(userData);
    }

    public User checkUser(LoginRequestDTO user){

        User storedUser = repository.findByEmail(user.email());
        if (storedUser != null) {
            if (passwordEncoder.matches(user.password(), storedUser.getPassword())) {
                return storedUser;
            }
        }
        return null;
    }


}
