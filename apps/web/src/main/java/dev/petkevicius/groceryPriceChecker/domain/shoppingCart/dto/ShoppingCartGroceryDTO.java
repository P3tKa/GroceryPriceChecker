package dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto;

import java.math.BigDecimal;

import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;

public record ShoppingCartGroceryDTO(
    String id,
    GroceryDTO grocery,
    BigDecimal quantity
) {
}
