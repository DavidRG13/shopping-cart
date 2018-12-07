package io.github.shopping.cart.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = "placedOrder")
@ToString
@NoArgsConstructor
@Entity
public class OrderLine {

    @Id
    private String id;

    private String productId;
    private int quantity;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlacedOrder placedOrder;

    public OrderLine(final String productId, final int quantity, final double price) {
        this(UUID.randomUUID().toString(), productId, quantity, price);
    }

    public OrderLine(final String id, final String productId, final int quantity, final double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotal() {
        return quantity * price;
    }
}
