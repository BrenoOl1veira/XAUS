package com.XAUS.services;

import com.XAUS.DTOS.OrderRequestDTO;
import com.XAUS.DTOS.OrdersResponseDTO;
import com.XAUS.Exceptions.NotFoundException;
import com.XAUS.Exceptions.OutOfStockException;
import com.XAUS.Models.Orders;
import com.XAUS.Models.Product;
import com.XAUS.Models.User;
import com.XAUS.repositories.OrdersRepository;
import com.XAUS.repositories.ProductRepository;
import com.XAUS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    public OrdersRepository repository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public UserRepository userRepository;



    public  Orders newOrder(@RequestBody OrderRequestDTO data) {

        User user = this.userRepository.findById(data.userId()).orElse(null);
        Product product = this.productRepository.findById(data.productId()).orElse(null);


        if (product == null) {
            throw new NotFoundException("Produto não encontrado.",  HttpStatus.NOT_FOUND);
        }

        if (user == null){
            throw new NotFoundException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        if (product.getQuantity() >= data.quantity()) {

            Orders newOrder = new Orders(data,data.quantity() * product.getPrice());
            if(data.clientId() == null){
                newOrder.setClientId(1L);
            }
            product.setQuantity(product.getQuantity() - data.quantity());
            productRepository.save(product);
            return repository.save(newOrder);
        } else {
            throw new OutOfStockException("Não há estoque suficiente para o produto selecionado.", HttpStatus.BAD_GATEWAY);
        }
    }


    public  List<OrdersResponseDTO> findById(Long orderID){

        List<OrdersResponseDTO> order =  repository.findBySomething(orderID, "order");

        if (order == null) {

            throw new NotFoundException("Pedido não encontrado.",  HttpStatus.NOT_FOUND);
        }

        return order;
    }

    public List<OrdersResponseDTO> OrdersByUserId(Long id){

        List<OrdersResponseDTO> orders = repository.findBySomething(id, "user");

        if (orders.isEmpty()){
            throw new NotFoundException("Nenhum pedido encontrado. ",  HttpStatus.NOT_FOUND);
        }

        return orders;
    }

    public List<OrdersResponseDTO> OrdersByClientId(Long id){

        List<OrdersResponseDTO> orders = repository.findBySomething(id, "client");


        if (orders.isEmpty()){
            throw new NotFoundException("Nenhum pedido encontrado. ",  HttpStatus.NOT_FOUND);
        }

        return orders;
    }

}
