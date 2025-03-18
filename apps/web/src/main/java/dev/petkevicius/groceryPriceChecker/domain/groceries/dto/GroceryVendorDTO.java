package dev.petkevicius.groceryPriceChecker.domain.groceries.dto;

import java.math.BigDecimal;

public record GroceryVendorDTO(
    VendorDTO vendorDto,
    BigDecimal price,
    BigDecimal priceWithDiscount,
    BigDecimal priceWithLoyaltyCard
) {

}
