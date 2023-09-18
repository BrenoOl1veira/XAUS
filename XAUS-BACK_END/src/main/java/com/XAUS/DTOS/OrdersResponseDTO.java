package com.XAUS.DTOS;

public interface OrdersResponseDTO {

    Long getOrderId();
    Long getUserId();
    Long getClientId();
    String getClientName();
    String getClientEmail();
    String getUserName();
    String getProductName();
    String getProductDescription();
    Float getProductPrice();
    Integer getOrderQuantity();
    Float getOrderPrice();
}


