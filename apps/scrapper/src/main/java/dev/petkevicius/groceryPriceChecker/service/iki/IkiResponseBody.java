package dev.petkevicius.groceryPriceChecker.service.iki;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IkiResponseBody {
    private String status;
    private List<ProductWrapper> products;
    private int count;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductWrapper {
        private FrontEndProduct frontEndProduct;
        private int position;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrontEndProduct {
        private String categoryId;
        private String chainId;
        private String conversionMeasure;
        private String countryOfOrigin;
        private String dateCreated;
        private double lastMonthLowestPrice;
        private int maximumOrderQuantity;
        private Nutrition nutrition;
        private String photoUrl;
        private String productId;
        private BigDecimal standardOrderQuantity;
        private String supplier;
        private String thumbUrl;
        private String unitOfMeasure;
        private Prc prc;
        private boolean isPromo;
        private boolean isInStock;
        private boolean isActive;
        private boolean isApproved;
        private Map<String, String> name;
        private Map<String, String> description;
        private Map<String, String> allergens;
        private Map<String, String> storingConditions;
        private Map<String, String> ingredients;
        private Map<String, Object> promoTags;
        private boolean hasNutritions;
        private double actualPrice;
        private double loyaltyPrice;
        private int conversionValue;
        private List<String> tags;
        private Object promo;
        private double depositPrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Nutrition {
        private String energy;
        private String fat;
        private String satFat;
        private String carbs;
        private String sugar;
        private String protein;
        private String salt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Prc {
        private BigDecimal p;
        private BigDecimal s;
        private BigDecimal l;
    }
}
