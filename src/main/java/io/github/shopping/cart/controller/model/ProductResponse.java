package io.github.shopping.cart.controller.model;

import lombok.Value;

@Value
public class ProductResponse {

    private String id;
    private String name;
    private double price;
}
