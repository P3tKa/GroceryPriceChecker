package dev.petkevicius.groceryPriceChecker.domain.groceries.dto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryUnit;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;

public record GroceryDTO(
    String id,
    String name,
    String countryOfOrigin,
    String supplier,
    Category category,
    Category.SubCategory subCategory,
    BigDecimal quantity,
    GroceryUnit unit,
    String imageUrl,
    List<GroceryVendorDTO> groceryVendorDTOS
) {
    public List<GroceryVendorDTO> sortVendorsByPrice() {
        return groceryVendorDTOS.stream()
            .sorted(Comparator.comparing(GroceryVendorDTO::price))
            .toList();
    }
}
