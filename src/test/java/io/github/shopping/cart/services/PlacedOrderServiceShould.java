package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreatePlacedOrderRequest;
import io.github.shopping.cart.controller.model.OrderLineRequest;
import io.github.shopping.cart.controller.model.PlacedOrderResponse;
import io.github.shopping.cart.repository.PlacedOrderRepository;
import io.github.shopping.cart.repository.ProductRepository;
import io.github.shopping.cart.repository.model.OrderLine;
import io.github.shopping.cart.repository.model.PlacedOrder;
import io.github.shopping.cart.repository.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlacedOrderServiceShould {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Test
    public void placeAnOrder() {
        // Given:
        productRepository.save(new Product("123", "laptop", 123.99));

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), asList(new OrderLineRequest("123", 2)));

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertTrue(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyWhenTryingToPlaceAnOrderWithAProductThatDoesntExists() {
        // Given:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), asList(new OrderLineRequest("123", 2)));

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyWhenTryingToPlaceAnOrderWithNoProducts() {
        // Given:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);
        final CreatePlacedOrderRequest createPlacedOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), Collections.emptyList());

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.placeOrder(createPlacedOrderRequest);

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }

    @Test
    public void returnExistingPlacedOrderByItsId() {
        // Given:
        placedOrderRepository.save(new PlacedOrder("123", "dede@dede.com", LocalDate.now(), asList(new OrderLine("456", 2, 3.99))));

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.getById("123");

        // Then:
        assertTrue(placedOrderResponse.isPresent());
    }

    @Test
    public void returnEmptyForANonExistingPlacedOrder() {
        // Given:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);

        // When:
        final Optional<PlacedOrderResponse> placedOrderResponse = placedOrderService.getById("123");

        // Then:
        assertFalse(placedOrderResponse.isPresent());
    }

    @Test
    public void shouldReturnOrderInPeriod() {
        // Given:
        final PlacedOrder savedOrderInOctober = new PlacedOrder("123", "dede@dede.com", LocalDate.parse("2018-10-02"), asList(new OrderLine("123", "456", 2, 3.99)));
        final PlacedOrder savedOrderInOctober2 = new PlacedOrder("456", "dede@dede.com", LocalDate.parse("2018-10-02"), asList(new OrderLine("456", "456", 2, 3.99)));
        final PlacedOrder savedOrderInNovember = new PlacedOrder("789", "dede@dede.com", LocalDate.parse("2018-11-01"), asList(new OrderLine("789", "456", 2, 3.99)));
        placedOrderRepository.saveAll(asList(savedOrderInOctober, savedOrderInOctober2, savedOrderInNovember));

        // And:
        final PlacedOrderService placedOrderService = new PlacedOrderService(placedOrderRepository, productRepository);

        // When:
        final List<PlacedOrderResponse> inPeriod = placedOrderService.getInPeriod(LocalDate.parse("2018-10-01"), LocalDate.parse("2018-10-03"));

        // Then:
        assertEquals(2, inPeriod.size());
    }
}