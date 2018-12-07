package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, String> {

    List<PlacedOrder> findByPlacedOnBetween(LocalDate from, LocalDate to);
}
