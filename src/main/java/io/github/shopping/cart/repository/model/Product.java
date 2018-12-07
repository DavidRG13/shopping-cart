package io.github.shopping.cart.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Product {

    @Id
    private String id;
    private String name;
    private double price;

    public Product(final String name, final double price) {
        this(UUID.randomUUID().toString(), name, price);
    }

    public Product(final String id, final String name, final double price) {
        this.name = name;
        this.price = price;
        this.id = id;
    }
}
