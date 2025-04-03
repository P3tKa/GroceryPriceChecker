package dev.petkevicius.groceryPriceChecker.domain.groceries.dto;

import java.util.List;

public record GroceryPageDTO(
    List<GroceryDTO> groceries,
    int currentPage,
    int totalPages
) {
}