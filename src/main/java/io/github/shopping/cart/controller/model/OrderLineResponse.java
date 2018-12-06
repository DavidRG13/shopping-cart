package io.github.shopping.cart.controller.model;

import io.github.shopping.cart.repository.model.OrderLine;
import lombok.Value;

@Value
public class OrderLineResponse {

    private String productId;
    private int quantity;
    private double pricePerUnit;

    public static OrderLineResponse from(final OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getProductId(), orderLine.getQuantity(), orderLine.getPrice());
    }

    public double getTotal() {
        return quantity * pricePerUnit;
    }
}
