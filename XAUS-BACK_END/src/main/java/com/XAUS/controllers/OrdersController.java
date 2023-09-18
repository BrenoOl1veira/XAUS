package com.XAUS.controllers;

import com.XAUS.DTOS.OrderRequestDTO;
import java.util.List;

import com.XAUS.DTOS.OrdersResponseDTO;
import com.XAUS.Models.Orders;
import com.XAUS.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrdersController {

    @Autowired
    public OrdersService ordersService;

    @PostMapping("/create")
    public Orders createNewOrder(@RequestBody OrderRequestDTO data){

        return ordersService.newOrder(data);

    }

        @GetMapping("/{orderId}")
    public  List<OrdersResponseDTO> getOrderBYId(@PathVariable Long orderId){

        return ordersService.findById(orderId);
    }

    @GetMapping("/byuser/{userId}")
    public List<OrdersResponseDTO> getOrdersByUserId(@PathVariable Long userId){

    return ordersService.OrdersByUserId(userId);
    }

    @GetMapping("/byclient/{clientId}")
    public List<OrdersResponseDTO> OrdersByClientId(@PathVariable Long clientId){

        return ordersService.OrdersByClientId(clientId);
    }


}
