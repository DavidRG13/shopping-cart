package io.github.shopping.cart.repository.model;

import lombok.Value;

@Value
public class OrderLine {

    private String productId;
    private int quantity;
    private double price;

    public double getTotal() {
        return quantity * price;
    }
}
