package dev.petkevicius.groceryPriceChecker.domain.groceries.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum Category implements CategoryType {
    FRUITS_AND_VEGETABLES(SubCategory.FRUITS_AND_BERRIES, SubCategory.VEGETABLES_AND_MUSHROOMS);

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

    Category(SubCategory... subCategories) {
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
        FRUITS_AND_BERRIES(SubSubCategory.BANANA),
        VEGETABLES_AND_MUSHROOMS(SubSubCategory.MUSHROOMS);

        private final SubSubCategory[] subSubCategories;

        SubCategory(SubSubCategory... subSubCategories) {
            this.subSubCategories = subSubCategories;
        }
    }

    public enum SubSubCategory implements CategoryType {
        BANANA,
        MUSHROOMS
    }
}