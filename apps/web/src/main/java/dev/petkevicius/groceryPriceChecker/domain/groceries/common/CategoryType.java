package dev.petkevicius.groceryPriceChecker.domain.groceries.common;

public sealed interface CategoryType permits Category, Category.SubCategory, Category.SubSubCategory {
}
