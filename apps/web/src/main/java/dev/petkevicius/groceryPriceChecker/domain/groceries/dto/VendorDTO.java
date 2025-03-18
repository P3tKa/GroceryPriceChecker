package dev.petkevicius.groceryPriceChecker.domain.groceries.dto;

import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;

public record VendorDTO(
    VendorName name,
    String imageUrl,
    String countryOfOrigin
) {
}
