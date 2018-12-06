package io.github.shopping.cart.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class PlacedOrder {

    private final String id;
    private final String buyerEmail;
    private final LocalDate placedOn;
    private final List<OrderLine> orderLines;

    public PlacedOrder(final String buyerEmail, final LocalDate placedOn, final List<OrderLine> orderLines) {
        this(UUID.randomUUID().toString(), buyerEmail, placedOn, orderLines);
    }

    public PlacedOrder(final String id, final String buyerEmail, final LocalDate placedOn, final List<OrderLine> orderLines) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.placedOn = placedOn;
        this.orderLines = orderLines;
    }

    public double getTotal() {
        return orderLines.stream()
            .mapToDouble(OrderLine::getTotal)
            .sum();
    }
}
