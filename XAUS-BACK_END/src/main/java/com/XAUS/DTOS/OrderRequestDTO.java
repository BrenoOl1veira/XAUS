package com.XAUS.DTOS;



public record OrderRequestDTO(Long productId, Long userId, Integer quantity, Long clientId) {

}
