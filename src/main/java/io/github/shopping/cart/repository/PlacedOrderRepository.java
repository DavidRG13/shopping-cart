package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, String> {

}
