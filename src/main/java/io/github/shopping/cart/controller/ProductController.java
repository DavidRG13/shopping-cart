package io.github.shopping.cart.controller;

import io.github.shopping.cart.controller.model.CreateProductRequest;
import io.github.shopping.cart.controller.model.ProductResponse;
import io.github.shopping.cart.controller.model.UpdateProductRequest;
import io.github.shopping.cart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns the desired product by providing its id.
     *
     * @param productId the id of the product to be returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") final String productId) {
        return productService.getProductById(productId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Creates a new product if the data provided is valid.
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid final CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    /**
     * Updates an existing product if the data provided is valid.
     *
     * @param productId the id of the product to be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") final String productId,
                                                         @RequestBody @Valid final UpdateProductRequest updateProductRequest) {
        return productService.updateProduct(productId, updateProductRequest)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
