package io.github.shopping.cart.controller.model;

import lombok.Value;

@Value
public class OrderLineRequest {

    private String productId;
    private int quantity;
}
