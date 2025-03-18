package dev.petkevicius.groceryPriceChecker.domain.groceries;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "groceries")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "groceryVendors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grocery {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "sub_category")
    @Enumerated(EnumType.STRING)
    private Category.SubCategory subCategory;

    @Column(name = "sub_sub_category")
    @Enumerated(EnumType.STRING)
    private Category.SubSubCategory subSubCategory;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "barcodes")
    private Set<Long> barcodes;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroceryUnit unit;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "grocery", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GroceryVendor> groceryVendors;
}

