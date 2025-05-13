package dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;

public record ShoppingCartDTO(
    String id,
    VendorName cheapestVendor,
    List<ShoppingCartGroceryDTO> shoppingCartGroceries
) {
}
