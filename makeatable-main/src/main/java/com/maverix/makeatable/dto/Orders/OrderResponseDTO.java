package com.maverix.makeatable.dto.Orders;


import lombok.Data;

@Data
public class OrderResponseDTO {
    private LastOrderDto lastOrder;

    public OrderResponseDTO(LastOrderDto lastOrder) {
        this.lastOrder = lastOrder;
    }
}
