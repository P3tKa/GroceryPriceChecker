package dev.petkevicius.groceryPriceChecker.domain.groceries.dto;

import java.math.BigDecimal;

public record CheapestVendorDTO(
    String name,
    String imageUrl,
    BigDecimal averagePrice
) {
}
