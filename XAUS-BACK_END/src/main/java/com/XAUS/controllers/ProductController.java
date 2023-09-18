package com.XAUS.controllers;


import com.XAUS.DTOS.ProductRequestDTO;
import com.XAUS.DTOS.ProductResponseDTO;
import com.XAUS.Models.Product;
import com.XAUS.services.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Informa que é um controller de request
@RestController
//Mapeia e request de product
@RequestMapping("products")
public class ProductController {

    //AutoWired injeta as depencias(instancia automacicamente uma classe)
    @Autowired
    public ProductService productService;

    //Mapping do método get e configura o cors para não dar erro no front
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getAll")
    public List<ProductResponseDTO> getAll() {

        return this.productService.getAll();

    }

    //Mapeia para post e configura o cors para não dar erro no front
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/create")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductRequestDTO data) {
        Product savedProduct = this.productService.saveProduct(data);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId){

        return this.productService.deleteProduct(productId);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO newData){
       return this.productService.updateProduct(productId, newData);
    }

    @PutMapping("/addStock/{productId}/{quantity}")
    public ResponseEntity addStock(@PathVariable Long productId, @PathVariable Integer quantity){
        return this.productService.addStock(productId, quantity);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id ){
        return this.productService.findById(id);
    }


}
