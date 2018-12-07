package io.github.shopping.cart.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
public class PlacedOrder {

    @Id
    private String id;
    private String buyerEmail;
    private LocalDate placedOn;

    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "placedOrder")
    private List<OrderLine> orderLines;

    public PlacedOrder(final String buyerEmail, final LocalDate placedOn, final List<OrderLine> orderLines) {
        this(UUID.randomUUID().toString(), buyerEmail, placedOn, orderLines);
    }

    public PlacedOrder(final String id, final String buyerEmail, final LocalDate placedOn, final List<OrderLine> orderLines) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.placedOn = placedOn;
        this.orderLines = orderLines;
        this.orderLines.forEach(it -> it.setPlacedOrder(this));
    }

    public double getTotal() {
        return orderLines.stream()
            .mapToDouble(OrderLine::getTotal)
            .sum();
    }
}
