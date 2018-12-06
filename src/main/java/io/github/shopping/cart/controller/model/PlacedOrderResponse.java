package io.github.shopping.cart.controller.model;

import io.github.shopping.cart.repository.model.PlacedOrder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class PlacedOrderResponse {

    private String buyerEmail;
    private LocalDate placedOn;
    private List<OrderLineResponse> lines;
    private double total;

    public static PlacedOrderResponse from(final PlacedOrder placedOrder) {
        final List<OrderLineResponse> orderLineResponses = placedOrder.getOrderLines().stream()
            .map(OrderLineResponse::from)
            .collect(Collectors.toList());

        final double total = orderLineResponses.stream().mapToDouble(OrderLineResponse::getTotal).sum();

        return new PlacedOrderResponse(placedOrder.getBuyerEmail(), placedOrder.getPlacedOn(), orderLineResponses, total);
    }

    public String getPlacedOn() {
        return placedOn.toString();
    }
}
