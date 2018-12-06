package io.github.shopping.cart.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class Product {

    private final String id;
    private final String name;
    private final double price;

    public Product(final String name, final double price) {
        this(UUID.randomUUID().toString(), name, price);
    }

    public Product(final String id, final String name, final double price) {
        this.name = name;
        this.price = price;
        this.id = id;
    }
}
