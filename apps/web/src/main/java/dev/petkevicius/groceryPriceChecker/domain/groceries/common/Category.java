package dev.petkevicius.groceryPriceChecker.domain.groceries.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum Category implements CategoryType {
    FRUITS_AND_VEGETABLES("Fruits and Vegetables", SubCategory.FRUITS_AND_BERRIES, SubCategory.VEGETABLES_AND_MUSHROOMS),
    MILK_PRODUCTS_AND_EGGS("Milk Products and Eggs", SubCategory.MILK);

    private final String displayName;
    private final SubCategory[] subCategories;

    private static final Map<SubSubCategory, SubCategory> subSubToSubMap = new HashMap<>();
    private static final Map<SubCategory, Category> subToCategoryMap = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            for (SubCategory subCategory : category.getSubCategories()) {
                subToCategoryMap.put(subCategory, category);
                for (SubSubCategory subSubCategory : subCategory.getSubSubCategories()) {
                    subSubToSubMap.put(subSubCategory, subCategory);
                }
            }
        }
    }

    Category(String displayName, SubCategory... subCategories) {
        this.displayName = displayName;
        this.subCategories = subCategories;
    }

    public static SubCategory getSubCategory(SubSubCategory subSubCategory) {
        return subSubToSubMap.get(subSubCategory);
    }

    public static Category getCategory(SubCategory subCategory) {
        return subToCategoryMap.get(subCategory);
    }

    public static Category getCategory(SubSubCategory subSubCategory) {
        SubCategory subCategory = getSubCategory(subSubCategory);
        return subToCategoryMap.get(subCategory);
    }

    @Getter
    public enum SubCategory implements CategoryType {
        FRUITS_AND_BERRIES("Fruits and Berries", SubSubCategory.BANANA),
        VEGETABLES_AND_MUSHROOMS("Vegetables and Mushrooms", SubSubCategory.MUSHROOMS),
        MILK("Milk", SubSubCategory.MILK_DRINKS);

        private final String displayName;
        private final SubSubCategory[] subSubCategories;

        SubCategory(String displayName, SubSubCategory... subSubCategories) {
            this.displayName = displayName;
            this.subSubCategories = subSubCategories;
        }
    }

    @Getter
    public enum SubSubCategory implements CategoryType {
        BANANA("Banana"),
        MUSHROOMS("Mushrooms"),
        MILK_DRINKS("Milk Drinks");

        private final String displayName;

        SubSubCategory(String displayName) {
            this.displayName = displayName;
        }
    }
}