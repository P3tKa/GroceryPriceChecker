package dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto;

import java.util.List;

public record ShoppingCartDTO(
    String id,
    List<ShoppingCartGroceryDTO> shoppingCartGroceries
) {
}
