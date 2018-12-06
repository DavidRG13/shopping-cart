package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreatePlacedOrderRequest;
import io.github.shopping.cart.controller.model.PlacedOrderResponse;
import io.github.shopping.cart.repository.PlacedOrderRepository;
import io.github.shopping.cart.repository.ProductRepository;
import io.github.shopping.cart.repository.model.OrderLine;
import io.github.shopping.cart.repository.model.PlacedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlacedOrderService {

    private final PlacedOrderRepository placedOrderRepository;
    private ProductRepository productRepository;

    @Autowired
    public PlacedOrderService(final PlacedOrderRepository placedOrderRepository, final ProductRepository productRepository) {
        this.placedOrderRepository = placedOrderRepository;
        this.productRepository = productRepository;
    }

    public Optional<PlacedOrderResponse> placeOrder(final CreatePlacedOrderRequest createPlacedOrderRequest) {
        if (createPlacedOrderRequest.getLines().isEmpty()) {
            return Optional.empty();
        }
        final boolean anyProductDoesntExists = createPlacedOrderRequest.getLines().stream().anyMatch(it -> !productRepository.findById(it.getProductId()).isPresent());
        if (anyProductDoesntExists) {
            return Optional.empty();
        }

        final List<OrderLine> orderLines = createPlacedOrderRequest.getLines().stream()
            .map(it -> new OrderLine(it.getProductId(), it.getQuantity(), productRepository.findById(it.getProductId()).get().getPrice()))
            .collect(Collectors.toList());
        final PlacedOrder orderToBePlaced = new PlacedOrder(createPlacedOrderRequest.getBuyerEmail(), createPlacedOrderRequest.getPlacedOn(), orderLines);

        return Optional.of(placedOrderRepository.save(orderToBePlaced))
            .map(PlacedOrderResponse::from);
    }

    public Optional<PlacedOrderResponse> getById(final String placedOrderId) {
        return placedOrderRepository.findById(placedOrderId)
            .map(PlacedOrderResponse::from);
    }
}
