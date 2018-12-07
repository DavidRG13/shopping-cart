package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreateProductRequest;
import io.github.shopping.cart.controller.model.ProductResponse;
import io.github.shopping.cart.controller.model.UpdateProductRequest;
import io.github.shopping.cart.repository.InMemoryProductRepo;
import io.github.shopping.cart.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceShould {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void createAProduct() {
        final ProductService productService = new ProductService(productRepository);

        final Optional<ProductResponse> actual = productService.createProduct(new CreateProductRequest("name", 12.2));

        assertTrue(actual.isPresent());
    }

    @Test
    public void findAnExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(productRepository);

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
        final ProductService productService = new ProductService(productRepository);

        final Optional<ProductResponse> doesntExists = productService.getProductById("doesntExists");

        assertFalse(doesntExists.isPresent());
    }

    @Test
    public void updateExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(productRepository);

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
        final ProductService productService = new ProductService(productRepository);

        // When:
        final Optional<ProductResponse> foundProduct = productService.updateProduct("doesntExistsId", new UpdateProductRequest("doesntExistsId", "laptop", 123.33));

        // Then:
        assertFalse(foundProduct.isPresent());
    }

    @Test
    public void updateAProductWithNotMatchingIds() {
        // Given:
        final ProductService productService = new ProductService(productRepository);

        // When:
        final Optional<ProductResponse> foundProduct = productService.updateProduct("doesntExistsId", new UpdateProductRequest("aDifferentId", "laptop", 123.33));

        // Then:
        assertFalse(foundProduct.isPresent());
    }

    @Test
    public void deleteExistingProduct() {
        // Given:
        final ProductService productService = new ProductService(productRepository);

        // And:
        final Optional<ProductResponse> existingProduct = productService.createProduct(new CreateProductRequest("name", 12.2));

        // When:
        productService.deleteProductById(existingProduct.get().getId());

        // Then:
        final Optional<ProductResponse> foundProduct = productService.getProductById(existingProduct.get().getId());
        assertFalse(foundProduct.isPresent());
    }
}