package dev.petkevicius.groceryPriceChecker.service.iki;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;

public class IkiConstants {
    public static final VendorName VENDOR_NAME = VendorName.IKI;
    public static final String COUNTRY_OF_ORIGIN = "LT";
    public static final URI IKI_URI = URI.create("https://search-952707942140.europe-north1.run.app/v1/frontend-products");

    public static final Map<CategoryType, List<String>> CATEGORY_IDS = new HashMap<>() {
        {
            // Fruits
            put(Category.SubSubCategory.BANANA, List.of("dXm2I2xsSMKPb34maRSX"));
            put(Category.SubSubCategory.AVOCADOS, List.of("vDZoX4YoRHKJp2fiFp0d"));
            put(Category.SubSubCategory.GRAPES, List.of("lK7sODURO01CWsoaBEVe"));
            put(Category.SubSubCategory.APPLE, List.of("jKUM60P3woQgmfwI7EdR"));
            put(Category.SubSubCategory.PEARS, List.of("hPXiMCFufLYC68myW64z"));
            put(Category.SubSubCategory.MELONS_AND_WATERMELONS,
                List.of(
                    "E3aTX1a3Ef9BWPon9Und", // Watermelons
                    "Gl7ZGnHk3b4dlw5nP2XE" // Melons
                )
            );
            put(Category.SubSubCategory.EXOTIC_FRUITS,
                List.of(
                    "5NDYI0G3vsV959mmLpkL", // Dates
                    "56COvQT1aNksHRp426is", // Kiwis
                    "ZgSmqFVEBQi70rrxqckG", // Pomegranates
                    "v3ps9vP3ja5DmZOUlc0Y", // Passionflower
                    "P7xyuwbRtRE5FVFe1xr8" // Exotic fruits
                )
            );
            put(Category.SubSubCategory.STONE_FRUIT, List.of("r6e4ozyqi48AyYVh6yPW"));

            // BERRIES
            put(Category.SubSubCategory.BERRIES, List.of(
                "TyLpkJrj4VvzdZTgry59",
                "7Z0ofDEO324fR7SY2SOK",
                "J9tUGWfkVwLDrBO40DEC",
                "8p87CHLyn8ieAGHm1Pay",
                "ek4vkMZwv39NsOzfOa6z",
                "iCegR8lVQhxNTP28Zyw0"
            ));

            // VEGETABLES
            put(Category.SubSubCategory.CUCUMBERS, List.of("ofL9nIXiarQ8oBAJeczM"));
            put(Category.SubSubCategory.TOMATOES, List.of("vUMLDemNwMbUJM0UWx4N"));
            put(Category.SubSubCategory.POTATOES, List.of("sJtx16jajsoTtamdixwy"));
            put(Category.SubSubCategory.ONIONS_LEEKS_AND_GARLIC,
                List.of(
                    "tUtPGLkqEjpQnMGRp4hI", // Onions, leeks
                    "UaH8ZH6rkzDyoSBXUyIx" // Garlic
                )
            );
            put(Category.SubSubCategory.CARROTS, List.of("Tf2kE5ysa0DAQrkKLQz9"));
            put(Category.SubSubCategory.BEETS_AND_OTHER_ROOT_VEGETABLES,
                List.of(
                    "iiZ8IdTP183Y2H5Mieyj", // Beets
                    "yFXatdH4nXTDzWZ5LCc6", // Other root vegetables
                    "eVlBcx4E13frxtDLAImg" // Radishes
                )
            );
            put(Category.SubSubCategory.CABBAGE, List.of("UotDb9BrtsiCxPJ3dMAP"));
            put(Category.SubSubCategory.SEASONED_VEGETABLES_AND_HERBS, List.of("G6cUl6GygNjWqvJnj1Rw"));
            put(Category.SubSubCategory.PEPPERS, List.of("gRRCe4XqwSfhKY9c3ygl"));
            put(Category.SubSubCategory.EGGPLANTS, List.of("8eVR1CMoNiHfFZBBpjvF"));
            put(Category.SubSubCategory.CORN_PEAS_BEANS_AND_LENTILS,
                List.of(
                    "rOtgAIampSoxg3lfxWX7", // Corns
                    "0VTJGDvyrnzFfMNiLPPj" // Peas and beans
                )
            );
            put(Category.SubSubCategory.PUMPKINS_AND_ZUCCHINI, List.of("AxUgDJHupD2BhqgTSuGv"));
            put(Category.SubSubCategory.SALAD_AND_THEIR_MIXTURES, List.of("LRMkYuph2eNqPqVTDnac"));
            put(Category.SubSubCategory.PROCESSED_VEGETABLES, List.of("zSCBbf0YLv4CMmUSx6Ez"));

            // MUSHROOMS
            put(Category.SubSubCategory.MUSHROOMS,
                List.of(
                    "VX72LbxC8cV0A1Kh40k6",
                    "lrpi3HTPsvVJ8zvDBNwj",
                    "ObYZq1RxdVXxbgSr591A"
                )
            );

            // MILK
            put(Category.SubSubCategory.LONG_LIFE_MILK, List.of("DFu7WwowDc7NeJNRIHmw"));
            put(Category.SubSubCategory.PASTEURIZED_MILK, List.of("B7UTvIzcguAYSjSplM0z"));
            put(Category.SubSubCategory.MILK_DRINKS, List.of("zf0UdfDZI7oGlDmMoe0N"));
            put(Category.SubSubCategory.CONDENSED_MILK, List.of("nj0qcjYDdDDcecG2qcQ7"));
            put(Category.SubSubCategory.CHICKEN_EGGS, List.of("ewKKA6euD2QXtN8OpYnu"));
            put(Category.SubSubCategory.QUAIL_EGGS, List.of("AI5QZEjMZPkhZZhyq7LT"));
        }
    };
}