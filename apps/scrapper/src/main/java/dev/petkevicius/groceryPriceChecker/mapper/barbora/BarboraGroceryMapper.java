package dev.petkevicius.groceryPriceChecker.mapper.barbora;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryUnit;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Vendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.mapper.GroceryMapper;
import dev.petkevicius.groceryPriceChecker.service.barbora.BarboraConstants;
import dev.petkevicius.groceryPriceChecker.service.barbora.BarboraProductResponse;
import org.springframework.stereotype.Component;

@Component
public class BarboraGroceryMapper  extends GroceryMapper {
    public List<Grocery> mapToGrocery(List<BarboraProductResponse> responseBody, CategoryType categoryType) {
        List<Grocery> groceries = new ArrayList<>();

        Vendor vendor = entityManager.getReference(Vendor.class, BarboraConstants.VENDOR_NAME);

        responseBody.stream()
            .filter(product -> product.getStatus().equalsIgnoreCase("active"))
            .forEach(product -> {

            GroceryUnit unit = GroceryUnit.fromString(
                product
                    .getComparative_unit().replaceAll("[^a-zA-Z]", "")
                    .trim()
            );

            BigDecimal price, priceWithDiscount, priceWithLoyalty;
            if (product.getRetail_price() != null) {
                price = product.getRetail_price();
                if ((product.getPromotion().isLoyaltyCardRequired())){
                    priceWithLoyalty = product.getComparative_unit_price_brutto();
                    priceWithDiscount = null;
                } else {
                    priceWithDiscount = product.getComparative_unit_price_brutto();
                    priceWithLoyalty = null;
                }
            } else {
                price = product.getPrice();
                priceWithDiscount = null;
                priceWithLoyalty = null;
            }

            BigDecimal quantity = priceWithDiscount != null ?
                priceWithDiscount.divide(product.getComparative_unit_price(), RoundingMode.HALF_DOWN) :
                (
                    priceWithLoyalty != null ?
                        priceWithLoyalty.divide(product.getComparative_unit_price(), RoundingMode.HALF_DOWN) :
                        price.divide(product.getComparative_unit_price_brutto(), RoundingMode.HALF_DOWN)
                );

            Grocery grocery = Grocery.builder()
                .id(UUID.randomUUID().toString())
                .name(product.getTitle())
                .countryOfOrigin(null) // Can't get from current BARBORA response
                .supplier(product.getBrand_name())
                .quantity(quantity)
                .unit(unit)
                .imageUrl(product.getImage())
                .build();

            setCategoryFields(grocery, categoryType);

            GroceryVendor groceryVendor = GroceryVendor.builder()
                .id(UUID.randomUUID().toString())
                .grocery(grocery)
                .groceryCode(product.getId())
                .vendor(vendor)
                .price(price)
                .priceWithDiscount(priceWithDiscount)
                .priceWithLoyaltyCard(priceWithLoyalty)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            grocery.setGroceryVendors(Set.of(groceryVendor));

            groceries.add(grocery);
        });

        return groceries;
    }
}
