package com.XAUS.services;

import com.XAUS.DTOS.ProductRequestDTO;
import com.XAUS.DTOS.ProductResponseDTO;
import com.XAUS.Exceptions.NotFoundException;
import com.XAUS.Models.Orders;
import com.XAUS.Models.Product;
import com.XAUS.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepository repository;

    public List<ProductResponseDTO> getAll() {
        //Metodo stream() pega tudo que vem do repositorio e coloca em um (funil) para mapearmos
        return repository.findAll().stream().map(ProductResponseDTO::new).toList();

    }

    public Product saveProduct(@RequestBody ProductRequestDTO data) {

        Product productData = new Product(data);
        return repository.save(productData);

    }

    public Product findById(Long id ){

        Product product = this.repository.findById(id).orElse(null);
        if(product == null){
            throw new NotFoundException("Produto não encontrado", HttpStatus.UNAUTHORIZED);
        }
        return product;
    }

    public ResponseEntity deleteProduct(Long id){

        //Just checking if its not null
        this.findById(id);

        repository.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    public ResponseEntity updateProduct(Long id, @RequestBody ProductRequestDTO newData){

        Product product = this.findById(id);

        product.setQuantity(newData.quantity());
        product.setName(newData.name());
        product.setDescription(newData.description());
        product.setPrice(newData.price());
        product.setQuantity(newData.quantity());
        repository.save(product);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity addStock (Long id, Integer quantity){

        Product product = this.findById(id);

        product.setQuantity(product.getQuantity() + quantity );
        repository.save(product);
        return ResponseEntity.ok().build();

    }


}
