package dev.petkevicius.groceryPriceChecker.domain.groceries;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groceries_vendors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroceryVendor {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grocery_id", nullable = false)
    private Grocery grocery;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "grocery_code", nullable = false, unique = true)
    private String groceryCode;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "price_with_discount")
    private BigDecimal priceWithDiscount;

    @Column(name = "price_with_loyalty_card")
    private BigDecimal priceWithLoyaltyCard;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @OneToMany(mappedBy = "groceryVendor", cascade = CascadeType.ALL)
    private List<GroceryPriceHistory> groceryPriceHistories;
}

