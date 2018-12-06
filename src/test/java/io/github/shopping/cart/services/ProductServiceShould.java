package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreateProductRequest;
import io.github.shopping.cart.controller.model.ProductResponse;
import io.github.shopping.cart.controller.model.UpdateProductRequest;
import io.github.shopping.cart.repository.InMemoryProductRepo;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ProductServiceShould {

    @Test
    public void createAProduct() {
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        final Optional<ProductResponse> actual = productService.createProduct(new CreateProductRequest("name", 12.2));

        assertTrue(actual.isPresent());
    }

    @Test
    public void findAnExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        // And:
        final Optional<ProductResponse> existingProduct = productService.createProduct(new CreateProductRequest("name", 12.2));

        // When:
        final Optional<ProductResponse> foundProduct = productService.getProductById(existingProduct.get().getId());

        // Then:
        assertTrue(foundProduct.isPresent());
        assertEquals(foundProduct.get().getName(), "name");
        assertEquals(foundProduct.get().getPrice(), 12.2, 0);
    }

    @Test
    public void returnEmptyIfIdNotFound() {
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        final Optional<ProductResponse> doesntExists = productService.getProductById("doesntExists");

        assertFalse(doesntExists.isPresent());
    }

    @Test
    public void updateExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        // And:
        final Optional<ProductResponse> existingProduct = productService.createProduct(new CreateProductRequest("name", 12.2));

        // When:
        final Optional<ProductResponse> foundProduct = productService.updateProduct(existingProduct.get().getId(), new UpdateProductRequest(existingProduct.get().getId(), "laptop", 123.33));

        // Then:
        assertTrue(foundProduct.isPresent());
        assertEquals(foundProduct.get().getName(), "laptop");
        assertEquals(foundProduct.get().getPrice(), 123.33, 0);
    }

    @Test
    public void updateANonExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        // When:
        final Optional<ProductResponse> foundProduct = productService.updateProduct("doesntExistsId", new UpdateProductRequest("doesntExistsId", "laptop", 123.33));

        // Then:
        assertFalse(foundProduct.isPresent());
    }

    @Test
    public void updateAProductWithNotMatchingIds() {
        // Given:
        final ProductService productService = new ProductService(new InMemoryProductRepo());

        // When:
        final Optional<ProductResponse> foundProduct = productService.updateProduct("doesntExistsId", new UpdateProductRequest("aDifferentId", "laptop", 123.33));

        // Then:
        assertFalse(foundProduct.isPresent());
    }
}