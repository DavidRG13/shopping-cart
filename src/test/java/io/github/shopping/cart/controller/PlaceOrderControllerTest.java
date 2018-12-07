package io.github.shopping.cart.controller;

import io.github.shopping.cart.controller.model.CreatePlacedOrderRequest;
import io.github.shopping.cart.controller.model.OrderLineRequest;
import io.github.shopping.cart.controller.model.OrderLineResponse;
import io.github.shopping.cart.controller.model.PlacedOrderResponse;
import io.github.shopping.cart.repository.model.OrderLine;
import io.github.shopping.cart.repository.model.PlacedOrder;
import io.github.shopping.cart.services.PlacedOrderService;
import io.github.shopping.internal.BaseControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceOrderControllerTest extends BaseControllerTest {

    @MockBean
    private PlacedOrderService placedOrderService;

    @Test
    public void shouldReturnTheNewlyPlacedOrder() throws Exception {
        final CreatePlacedOrderRequest placeOrderRequest = new CreatePlacedOrderRequest("dede@dede.com", LocalDate.now(), asList(new OrderLineRequest("123", 2)));
        final PlacedOrderResponse expectedResponse = new PlacedOrderResponse("dede@dede.com", placeOrderRequest.getPlacedOn(), asList(new OrderLineResponse("456", 2, 3.99)), 7.98);
        given(this.placedOrderService.placeOrder(placeOrderRequest))
            .willReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(
            post("/placed-orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeOrderRequest)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void shouldFailWhenSubmittingInvalidValues() throws Exception {
        final String invalidBuyerEmail = "dede";
        final CreatePlacedOrderRequest placeOrderRequest = new CreatePlacedOrderRequest(invalidBuyerEmail, LocalDate.now(), asList(new OrderLineRequest("123", 2)));

        this.mockMvc.perform(
            post("/placed-orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeOrderRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAnExistingPlacedOrder() throws Exception {
        final PlacedOrderResponse expectedResponse = new PlacedOrderResponse("dede@dede.com", LocalDate.now(), asList(new OrderLineResponse("456", 2, 3.99)), 7.98);

        given(this.placedOrderService.getById("123"))
            .willReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(
            get("/placed-orders/{id}", 123)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void shouldReturnNotFoundForAnUnknownPlacedOrder() throws Exception {
        given(this.placedOrderService.getById("123"))
            .willReturn(Optional.empty());

        this.mockMvc.perform(
            get("/placed-orders/{id}", 123)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnPlacedOrdersInPeriod() throws Exception {
        final PlacedOrderResponse savedOrderInOctober = new PlacedOrderResponse("dede@dede.com", LocalDate.parse("2018-10-02"), asList(new OrderLineResponse("456", 2, 3.99)), 7.98);
        final PlacedOrderResponse savedOrderInOctober2 = new PlacedOrderResponse("dede@dede.com", LocalDate.parse("2018-10-02"), asList(new OrderLineResponse("789", 2, 3.99)), 7.98);

        final List<PlacedOrderResponse> expectedPlacedOrders = asList(savedOrderInOctober, savedOrderInOctober2);
        given(this.placedOrderService.getInPeriod(LocalDate.parse("2018-10-01"), LocalDate.parse("2018-10-03")))
            .willReturn(expectedPlacedOrders);

        this.mockMvc.perform(
            get("/placed-orders")
                .param("from", "2018-10-01")
                .param("to", "2018-10-03")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedPlacedOrders)));
    }
}