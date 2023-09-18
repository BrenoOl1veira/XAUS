package com.XAUS.controllers;


import com.XAUS.DTOS.LoginRequestDTO;
import com.XAUS.DTOS.LoginResponseDTO;
import com.XAUS.DTOS.UserRequestDTO;
import com.XAUS.DTOS.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.XAUS.Models.User;
import com.XAUS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    public UserService userService;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getAll")
    public  List<UserResponseDTO>  getAll() {
    return this.userService.getAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/register")
    public  User  createNewUser(@RequestBody UserRequestDTO data) {
       return this.userService.saveNewUser(data);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> Login(@RequestBody LoginRequestDTO user ){
        User loggedUser = this.userService.checkUser(user);

        if (loggedUser != null) {
            LoginResponseDTO response = new LoginResponseDTO(loggedUser, "Logado com sucesso!");
            return ResponseEntity.ok(response);
        } else {
            LoginResponseDTO response = LoginResponseDTO.createUnauthorizedResponse();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


}
