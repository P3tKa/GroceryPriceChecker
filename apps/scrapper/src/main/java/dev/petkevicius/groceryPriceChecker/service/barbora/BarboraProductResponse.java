package dev.petkevicius.groceryPriceChecker.service.barbora;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarboraProductResponse {
    private String id;
    private String title;
    private String brand_name;
    private String status;
    private String image;
    private BigDecimal price;
    private BigDecimal retail_price;
    private String comparative_unit;
    private BigDecimal comparative_unit_price;
    private BigDecimal comparative_unit_price_brutto;
    private Promotion promotion;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Promotion {
        private boolean loyaltyCardRequired;
    }

}
