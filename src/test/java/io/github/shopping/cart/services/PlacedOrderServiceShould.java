package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreatePlacedOrderRequest;
import io.github.shopping.cart.controller.model.OrderLineRequest;
import io.github.shopping.cart.controller.model.PlacedOrderResponse;
import io.github.shopping.cart.repository.InMemoryPlacedOrderRepo;
import io.github.shopping.cart.repository.InMemoryProductRepo;
import io.github.shopping.cart.repository.model.OrderLine;
import io.github.shopping.cart.repository.model.PlacedOrder;
import io.github.shopping.cart.repository.model.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class PlacedOrderServiceShould {

    @Test
    public void placeAnOrder() {
        // Given:
        final InMemoryProductRepo productRepository = new InMemoryProductRepo();
        productRepository.save(new Product("123", "laptop", 123.99));

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(new InMemoryPlacedOrderRepo(), productRepository);
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), asList(new OrderLineRequest("123", 2)));

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertTrue(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyWhenTryingToPlaceAnOrderWithAProductThatDoesntExists() {
        // Given:
        final InMemoryProductRepo productRepository = new InMemoryProductRepo();

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(new InMemoryPlacedOrderRepo(), productRepository);
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), asList(new OrderLineRequest("123", 2)));

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyWhenTryingToPlaceAnOrderWithNoProducts() {
        // Given:
        final PlacedOrderService placedOrderService = new PlacedOrderService(new InMemoryPlacedOrderRepo(), new InMemoryProductRepo());
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), Collections.emptyList());

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }

    @Test
    public void returnExistingPlacedOrderByItsId() {
        // Given:
        final InMemoryPlacedOrderRepo placedOrderRepository = new InMemoryPlacedOrderRepo();
        placedOrderRepository.save(new PlacedOrder("123", "dede@dede.com", LocalDate.now(), asList(new OrderLine("456", 2, 3.99))));

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, new InMemoryProductRepo());

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.getById("123");

        // Then:
        assertTrue(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyForANonExistingPlacedOrder() {
        // Given:
        final PlacedOrderService placedOrderService = new PlacedOrderService(new InMemoryPlacedOrderRepo(), new InMemoryProductRepo());

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.getById("123");

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }
}