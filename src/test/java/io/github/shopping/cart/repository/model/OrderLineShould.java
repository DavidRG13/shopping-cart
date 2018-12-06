package io.github.shopping.cart.repository.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderLineShould {

    @Test
    public void calculateTheTotalPrice() {
        final OrderLine orderLine = new OrderLine("123", 2, 3.5);

        assertEquals(7, orderLine.getTotal(), 0);
    }
}