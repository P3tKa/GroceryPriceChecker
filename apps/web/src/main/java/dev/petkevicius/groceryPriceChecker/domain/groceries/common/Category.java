package dev.petkevicius.groceryPriceChecker.domain.groceries.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum Category implements CategoryType {
    FRUITS_AND_VEGETABLES(
        "Fruits and Vegetables",
        SubCategory.FRUITS_AND_BERRIES,
        SubCategory.VEGETABLES_AND_MUSHROOMS
    ),
    MILK_PRODUCTS_AND_EGGS(
        "Milk Products and Eggs",
        SubCategory.MILK,
        SubCategory.EGGS
    );

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
        FRUITS_AND_BERRIES(
            "Fruits and Berries",
            SubSubCategory.BANANA,
            SubSubCategory.LEMON,
            SubSubCategory.APPLE,
            SubSubCategory.PEARS,
            SubSubCategory.GRAPES,
            SubSubCategory.STONE_FRUIT,
            SubSubCategory.BERRIES,
            SubSubCategory.AVOCADOS,
            SubSubCategory.ORANGES,
            SubSubCategory.GRAPEFRUITS,
            SubSubCategory.TANGERINES
        ),
        VEGETABLES_AND_MUSHROOMS(
            "Vegetables and Mushrooms",
            SubSubCategory.TOMATOES,
            SubSubCategory.CUCUMBERS,
            SubSubCategory.PEPPERS,
            SubSubCategory.PUMPKINS_AND_ZUCCHINI,
            SubSubCategory.SALAD_AND_THEIR_MIXTURES,
            SubSubCategory.POTATOES,
            SubSubCategory.BEETS_AND_OTHER_ROOT_VEGETABLES,
            SubSubCategory.CORN_PEAS_BEANS_AND_LENTILS,
            SubSubCategory.ONIONS_LEEKS_AND_GARLIC,
            SubSubCategory.SEASONED_VEGETABLES_AND_HERBS,
            SubSubCategory.MUSHROOMS,
            SubSubCategory.PROCESSED_VEGETABLES,
            SubSubCategory.CABBAGE,
            SubSubCategory.CARROTS,
            SubSubCategory.EGGPLANTS
        ),
        MILK(
            "Milk",
            SubSubCategory.LONG_LIFE_MILK,
            SubSubCategory.PASTEURIZED_MILK,
            SubSubCategory.MILK_DRINKS,
            SubSubCategory.CONDENSED_MILK
        ),
        EGGS("Eggs", SubSubCategory.CHICKEN_EGGS);

        private final String displayName;
        private final SubSubCategory[] subSubCategories;

        SubCategory(String displayName, SubSubCategory... subSubCategories) {
            this.displayName = displayName;
            this.subSubCategories = subSubCategories;
        }
    }

    @Getter
    public enum SubSubCategory implements CategoryType {
        // Fruits and Berries
        BANANA("Banana"),
        LEMON("Lemon"),
        EXOTIC_FRUITS("Exotic Fruits"),
        MELONS_AND_WATERMELONS("Melons and Watermelons"),
        APPLE("Apple"),
        PEARS("Pears"),
        GRAPES("Grapes"),
        STONE_FRUIT("Stone Fruit"),
        BERRIES("Berries"),
        AVOCADOS("Avocados"),
        ORANGES("Oranges"),
        GRAPEFRUITS("Grapefruits"),
        TANGERINES("Tangerines"),

        // VEGETABLES_AND_MUSHROOMS
        TOMATOES("Tomatoes"),
        CUCUMBERS("Cucumbers"),
        PEPPERS("Peppers"),
        PUMPKINS_AND_ZUCCHINI("Pumpkins and Zucchini"),
        SALAD_AND_THEIR_MIXTURES("Salad and Their Mixtures"),
        POTATOES("Potatoes"),
        BEETS_AND_OTHER_ROOT_VEGETABLES("Beets and other root vegetables"),
        CORN_PEAS_BEANS_AND_LENTILS("Corn, Peas, Beans and Lentils"),
        ONIONS_LEEKS_AND_GARLIC("Onions, Leeks and Garlic"),
        SEASONED_VEGETABLES_AND_HERBS("Seasoned Vegetables and Herbs"),
        MUSHROOMS("Mushrooms"),
        PROCESSED_VEGETABLES("Processed Vegetables"),
        CABBAGE("Cabbage"),
        CARROTS("Carrots"),
        EGGPLANTS("Eggplants"),

        // MILK
        LONG_LIFE_MILK("Long Life Milk"),
        PASTEURIZED_MILK("Pasteurized Milk"),
        MILK_DRINKS("Milk Drinks"),
        CONDENSED_MILK("Condensed Milk"),

        // EGGS
        CHICKEN_EGGS("Chicken Eggs");

        private final String displayName;

        SubSubCategory(String displayName) {
            this.displayName = displayName;
        }
    }
}