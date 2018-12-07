package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldInsertAPlacedOrder() {
        final Product savedProduct = new Product("123", "laptop", 199.99);
        productRepository.save(savedProduct);

        final Optional<Product> actualOrder = productRepository.findById("123");

        assertTrue(actualOrder.isPresent());
        assertEquals(savedProduct, actualOrder.get());
    }

    @Test
    public void shouldDeleteAProduct() {
        final Product savedProduct = new Product("123", "laptop", 199.99);
        productRepository.save(savedProduct);

        productRepository.deleteById("123");

        final Optional<Product> actualOrder = productRepository.findById("123");
        assertFalse(actualOrder.isPresent());
    }
}