package io.github.shopping.cart.services;

import io.github.shopping.cart.controller.model.CreateProductRequest;
import io.github.shopping.cart.controller.model.ProductResponse;
import io.github.shopping.cart.controller.model.UpdateProductRequest;
import io.github.shopping.cart.repository.ProductRepository;
import io.github.shopping.cart.repository.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductResponse> getProductById(final String productId) {
        return productRepository.findById(productId)
            .map(it -> new ProductResponse(it.getId(), it.getName(), it.getPrice()));
    }

    public Optional<ProductResponse> createProduct(final CreateProductRequest createProductRequest) {
        final Product productToBeSaved = new Product(createProductRequest.getName(), createProductRequest.getPrice());
        return Optional.of(productRepository.save(productToBeSaved))
            .map(it -> new ProductResponse(it.getId(), it.getName(), it.getPrice()));
    }

    public Optional<ProductResponse> updateProduct(final String productId, final UpdateProductRequest updateProductRequest) {
        if (!productId.equals(updateProductRequest.getId()) || !productRepository.existsById(productId)) {
            return Optional.empty();
        }
        final Product productToBeUpdated = new Product(updateProductRequest.getId(), updateProductRequest.getName(), updateProductRequest.getPrice());
        return Optional.of(productRepository.save(productToBeUpdated))
            .map(it -> new ProductResponse(it.getId(), it.getName(), it.getPrice()));
    }

    public void deleteProductById(final String productId) {
        productRepository.deleteById(productId);
    }
}
