package dev.petkevicius.groceryPriceChecker.service.iki;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IkiRequestBody {
    private final int limit = 60;
    private final boolean personalized = false;
    private final Params params = new Params(List.of());
    private int fromIndex;

    @Data
    @AllArgsConstructor
    public static class Params {
        private final String type = "view_products";
        private final boolean isActive = true;
        private final boolean isApproved = true;
        private final Map<String, Object> filter = Map.of();
        private final List<String> chainIds = List.of("CvKfTzV4TN5U8BTMF1Hl");
        private final List<String> storeIds = List.of();
        private List<String> categoryIds;
        private final boolean isUsingStock = true;

        public void setCategoryId(String categoryId) {
            this.categoryIds = List.of(categoryId);
        }
    }

    public void setCategoryId(String categoryId) {
        this.params.setCategoryId(categoryId);
    }
}