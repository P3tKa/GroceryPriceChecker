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
            put(Category.SubCategory.FRUITS_AND_BERRIES, List.of("iSYTdWjFGh5YWglqphtj", "TyLpkJrj4VvzdZTgry59"));
            put(Category.SubCategory.VEGETABLES_AND_MUSHROOMS, List.of(
                "R9SDZX71J0Qapuvm1Lgp",
                "tRJToqydPFMhQE2FKdWa",
                "ofL9nIXiarQ8oBAJeczM",
                "vUMLDemNwMbUJM0UWx4N",
                "sJtx16jajsoTtamdixwy",
                "hOA2cJhlKE9jSCMlKRnG",
                "tUtPGLkqEjpQnMGRp4hI",
                "UaH8ZH6rkzDyoSBXUyIx",
                "Tf2kE5ysa0DAQrkKLQz9",
                "iiZ8IdTP183Y2H5Mieyj",
                "yFXatdH4nXTDzWZ5LCc6",
                "eVlBcx4E13frxtDLAImg",
                "UotDb9BrtsiCxPJ3dMAP",
                "G6cUl6GygNjWqvJnj1Rw",
                "gRRCe4XqwSfhKY9c3ygl",
                "8eVR1CMoNiHfFZBBpjvF",
                "ypyD9IuHAP76folk0D6c",
                "rOtgAIampSoxg3lfxWX7",
                "0VTJGDvyrnzFfMNiLPPj",
                "AxUgDJHupD2BhqgTSuGv",
                "LRMkYuph2eNqPqVTDnac",
                "zSCBbf0YLv4CMmUSx6Ez",
                "VX72LbxC8cV0A1Kh40k6"
            ));
        }
    };
}