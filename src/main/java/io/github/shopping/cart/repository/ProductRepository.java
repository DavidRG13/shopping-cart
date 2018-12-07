package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
