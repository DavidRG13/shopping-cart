package io.github.shopping.cart.controller;

import io.github.shopping.cart.controller.model.CreateProductRequest;
import io.github.shopping.cart.controller.model.ProductResponse;
import io.github.shopping.cart.controller.model.UpdateProductRequest;
import io.github.shopping.cart.services.ProductService;
import io.github.shopping.internal.BaseControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest extends BaseControllerTest {

    @MockBean
    private ProductService productService;

    @Test
    public void shouldReturnTheNewlyCreatedProduct() throws Exception {
        final CreateProductRequest productRequest = new CreateProductRequest("laptop", 199.99);
        final ProductResponse expectedResponse = new ProductResponse("123", "laptop", 199.99);
        given(this.productService.createProduct(productRequest))
            .willReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(
            post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void shouldFailWhenSubmittingInvalidValues() throws Exception {
        final String tooShortProductName = "la";
        final CreateProductRequest productRequest = new CreateProductRequest(tooShortProductName, 199.99);

        this.mockMvc.perform(
            post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAnExistingProduct() throws Exception {
        final ProductResponse expectedResponse = new ProductResponse("123", "laptop", 199.99);
        given(this.productService.getProductById("123"))
            .willReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(
            get("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void shouldFailWhenAskingForAProductIdThatDoesntExist() throws Exception {
        given(this.productService.getProductById("123"))
            .willReturn(Optional.empty());

        this.mockMvc.perform(
            get("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateAnExistingProduct() throws Exception {
        final ProductResponse expectedResponse = new ProductResponse("123", "laptop", 149.99);
        final UpdateProductRequest updateProductRequest = new UpdateProductRequest("123", "laptop", 149.99);
        given(this.productService.updateProduct("123", updateProductRequest))
            .willReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(
            put("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequest)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    public void shouldFailWhenUpdatingAProductWithInvalidData() throws Exception {
        final double negativeInvalidPrice = -2.99;
        final UpdateProductRequest updateProductRequest = new UpdateProductRequest("123", "laptop", negativeInvalidPrice);

        this.mockMvc.perform(
            put("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailWhenUpdatingAProductIdThatDoesntExist() throws Exception {
        final UpdateProductRequest updateProductRequest = new UpdateProductRequest("123", "laptop", 149.99);
        given(this.productService.updateProduct("123", updateProductRequest))
            .willReturn(Optional.empty());

        this.mockMvc.perform(
            put("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequest)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAnExistingProduct() throws Exception {
        this.mockMvc.perform(
            delete("/products/{id}", 123)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}