package io.github.shopping.cart.controller;

import io.github.shopping.cart.controller.model.CreatePlacedOrderRequest;
import io.github.shopping.cart.controller.model.PlacedOrderResponse;
import io.github.shopping.cart.services.PlacedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/placed-orders")
public class PlaceOrderController {

    private final PlacedOrderService placedOrderService;

    @Autowired
    public PlaceOrderController(final PlacedOrderService placedOrderService) {
        this.placedOrderService = placedOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacedOrderResponse> getPlacedOrder(@PathVariable("id") final String placedOrderId) {
        return placedOrderService.getById(placedOrderId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlacedOrderResponse>> getPlacedOrdersInPeriod(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate from,
                                                              @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate to) {
        final List<PlacedOrderResponse> inPeriod = placedOrderService.getInPeriod(from, to);
        if (inPeriod.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inPeriod);
    }

    @PostMapping
    public ResponseEntity<PlacedOrderResponse> placeOrder(@RequestBody @Valid final CreatePlacedOrderRequest createPlacedOrderRequest) {
        return placedOrderService.placeOrder(createPlacedOrderRequest)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
