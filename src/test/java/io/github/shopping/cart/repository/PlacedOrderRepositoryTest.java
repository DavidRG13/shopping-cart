package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.OrderLine;
import io.github.shopping.cart.repository.model.PlacedOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PlacedOrderRepositoryTest {

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Test
    public void shouldInsertAPlacedOrder() {
        final PlacedOrder savedOrder = new PlacedOrder("123", "dede@dede.com", LocalDate.now(), asList(new OrderLine("123", "456", 2, 3.99)));
        placedOrderRepository.save(savedOrder);

        final Optional<PlacedOrder> actualOrder = placedOrderRepository.findById("123");

        assertTrue(actualOrder.isPresent());
        assertEquals(savedOrder, actualOrder.get());
    }
}