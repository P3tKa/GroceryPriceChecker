package dev.petkevicius.groceryPriceChecker.domain.groceries;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grocery_price_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroceryPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_vendor_id", nullable = false)
    private GroceryVendor groceryVendor;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "price_with_discount")
    private BigDecimal priceWithDiscount;

    @Column(name = "price_with_loyalty_card")
    private BigDecimal priceWithLoyaltyCard;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}