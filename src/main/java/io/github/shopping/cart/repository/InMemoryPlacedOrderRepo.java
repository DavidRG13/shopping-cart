package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.PlacedOrder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryPlacedOrderRepo implements PlacedOrderRepository {

    private final Map<String, PlacedOrder> placedOrders = new HashMap<>();

    @Override
    public PlacedOrder save(final PlacedOrder placedOrder) {
        placedOrders.putIfAbsent(placedOrder.getId(), placedOrder);
        return placedOrder;
    }

    @Override
    public Optional<PlacedOrder> update(final PlacedOrder product) {
        throw new UnsupportedOperationException("Not needed for this product");
    }

    @Override
    public Optional<PlacedOrder> findById(final String productId) {
        return Optional.ofNullable(placedOrders.get(productId));
    }
}
