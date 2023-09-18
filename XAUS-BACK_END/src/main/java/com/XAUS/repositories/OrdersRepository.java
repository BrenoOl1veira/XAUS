package com.XAUS.repositories;
import com.XAUS.DTOS.OrdersResponseDTO;
import com.XAUS.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository já trás todos os métodos de acesso ao db sem ter que implementar nada, alem do tipo do repository que ela retorna
//E o tipo do id

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query(value = "SELECT o.id AS orderId, o.user_id AS userId, u.name AS userName, " +
            "p.name AS productName, p.description AS productDescription, o.client_id as clientId, c.email as clientEmail, " +
            "c.name as clientName ,p.price AS productPrice, o.quantity AS orderQuantity, o.order_price AS orderPrice " +
            "FROM orders AS o " +
            "LEFT JOIN products AS p ON p.id = o.product_id " +
            "LEFT JOIN users AS u ON u.id = o.user_id " +
            "LEFT JOIN clients AS c ON c.id = o.client_id " +
            "WHERE (:param_type = 'user' AND o.user_id = :id) OR (:param_type = 'client' AND o.client_id = :id)"+
            "OR (:param_type = 'order' AND o.id = :id)", nativeQuery = true)
    public List<OrdersResponseDTO> findBySomething(@Param("id") Long id, @Param("param_type") String paramType);


}

