package com.XAUS.services;

import com.XAUS.DTOS.ClientsRequestDTO;
import com.XAUS.Exceptions.NotFoundException;
import com.XAUS.Models.Clients;
import com.XAUS.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientsService {

    @Autowired
    ClientsRepository repository;

    public Clients newClient(@RequestBody ClientsRequestDTO data) {

        Clients clientData = new Clients(data);
        return repository.save(clientData);

    }

    public Clients findClientyByCPF(String CPF){

        return repository.findbyCPF(CPF);

    }

    public Clients findById(Long id){

        Clients client =  repository.findById(id).orElse(null);

        if(client == null){
            throw new NotFoundException("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return client;
    }

    public Clients findbyEmail(String Email){

        return repository.findbyEmail(Email);

    }

    public ResponseEntity updateClient (Long id, @RequestBody ClientsRequestDTO newData){

        Clients client = this.findById(id);

        client.setCpf(newData.cpf());
        client.setEmail(newData.email());
        client.setName(newData.name());
        repository.save(client);
        return ResponseEntity.ok().build();
    }


}
