package io.github.shopping.cart.controller.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateProductRequest {

    /*
     * Name of the product.
     */
    @Size(min = 5, max = 40)
    private String name;

    /*
     * Price per unit of the product.
     */
    @PositiveOrZero
    private double price;
}
