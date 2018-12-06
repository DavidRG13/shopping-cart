package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryProductRepo implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Product save(final Product product) {
        products.putIfAbsent(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> update(final Product product) {
        if (!products.containsKey(product.getId())) {
            return Optional.empty();
        }
        products.put(product.getId(), product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(final String productId) {
        return Optional.ofNullable(products.get(productId));
    }
}
