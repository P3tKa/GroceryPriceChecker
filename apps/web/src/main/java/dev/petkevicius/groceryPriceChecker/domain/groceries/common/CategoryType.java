package dev.petkevicius.groceryPriceChecker.domain.groceries.common;

public sealed interface CategoryType permits Category, Category.SubCategory, Category.SubSubCategory {

    static CategoryType fromString(String category) {
        String upperCaseCategory = category.toUpperCase();

        for (CategoryType[] types : new CategoryType[][] {
            Category.values(),
            Category.SubCategory.values(),
            Category.SubSubCategory.values()
        }) {
            for (CategoryType type : types) {
                if (type instanceof Enum<?> enumValue && enumValue.name().equals(upperCaseCategory)) {
                    return type;
                }
            }
        }

        throw new IllegalArgumentException("Category not found: " + category);
    }
}
