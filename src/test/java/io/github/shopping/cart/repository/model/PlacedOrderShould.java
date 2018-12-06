package io.github.shopping.cart.repository.model;

import org.junit.Test;

import java.time.LocalDate;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class PlacedOrderShould {

    @Test
    public void calculateGrandTotal() {
        final OrderLine orderLine1 = new OrderLine("123", 2, 3.5);
        final OrderLine orderLine2 = new OrderLine("456", 2, 3.5);
        final PlacedOrder placedOrder = new PlacedOrder("id", "dede@dede.com", LocalDate.now(), asList(orderLine1, orderLine2));

        assertEquals(14, placedOrder.getTotal(), 0);
    }
}