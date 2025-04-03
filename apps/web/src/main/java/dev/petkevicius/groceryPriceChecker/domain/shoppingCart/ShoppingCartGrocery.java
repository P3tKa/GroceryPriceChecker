package dev.petkevicius.groceryPriceChecker.domain.shoppingCart;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "basket_items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShoppingCartGrocery {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private String id;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grocery_id", nullable = false)
    private Grocery grocery;

    private BigDecimal quantity;
}
