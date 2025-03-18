package dev.petkevicius.groceryPriceChecker.domain.groceries;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vendor {

    @Id
    @Column(name = "id")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    VendorName id;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "country_of_origin")
    String countryOfOrigin;

    @OneToMany(mappedBy = "vendor")
    private Set<GroceryVendor> groceryVendors;
}

