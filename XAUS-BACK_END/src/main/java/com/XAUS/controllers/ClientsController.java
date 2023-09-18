package com.XAUS.controllers;

import com.XAUS.DTOS.ClientsRequestDTO;
import com.XAUS.DTOS.ProductRequestDTO;
import com.XAUS.Exceptions.NotFoundException;
import com.XAUS.Models.Clients;
import com.XAUS.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    ClientsService clientsService;


    @PostMapping("/create")
    public Clients createNewClient(@RequestBody ClientsRequestDTO data){

        Clients alreadyAddedCPF = clientsService.findClientyByCPF(data.cpf());
        Clients alreadyAddedEmail = clientsService.findbyEmail(data.email());


        if(alreadyAddedCPF != null  ){
            throw new NotFoundException("CPF já cadastrado", HttpStatus.BAD_GATEWAY);
        }
        if(alreadyAddedEmail != null){
            throw new NotFoundException("Email já cadastrado", HttpStatus.BAD_GATEWAY);
        }

        return this.clientsService.newClient(data);

    }

    @GetMapping("/bycpf/{CPF}")
    public Clients getClientByCPF(@PathVariable("CPF") String CPF){

        return this.clientsService.findClientyByCPF(CPF);
    }

    @GetMapping("{id}")
    public Clients findById(@PathVariable Long id ){
        return this.clientsService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody ClientsRequestDTO newData){
        return this.clientsService.updateClient(id, newData);
    }
}
