package dev.petkevicius.groceryPriceChecker.mapper.iki;

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
import dev.petkevicius.groceryPriceChecker.service.iki.IkiConstants;
import dev.petkevicius.groceryPriceChecker.service.iki.IkiResponseBody;
import org.springframework.stereotype.Component;

@Component
public class IkiGroceryMapper extends GroceryMapper {

    public List<Grocery> mapToGrocery(IkiResponseBody responseBody, CategoryType categoryType) {
        List<Grocery> groceries = new ArrayList<>();

        Vendor vendor = entityManager.getReference(Vendor.class, IkiConstants.VENDOR_NAME);

        responseBody.getProducts().stream()
            .map(IkiResponseBody.ProductWrapper::getFrontEndProduct)
            .filter(product -> product.getPrc().getP() != null)
            .forEach(product -> {
                    Grocery grocery = Grocery.builder()
                        .id(UUID.randomUUID().toString())
                        .name(product.getName().get("lt"))
                        .countryOfOrigin(product.getCountryOfOrigin())
                        .supplier(product.getSupplier())
                        .quantity(product.getStandardOrderQuantity())
                        .unit(GroceryUnit.fromString(product.getUnitOfMeasure()))
                        .imageUrl(product.getPhotoUrl())
                        .build();

                    setCategoryFields(grocery, categoryType);

                    GroceryVendor groceryVendor = GroceryVendor.builder()
                        .id(UUID.randomUUID().toString())
                        .grocery(grocery)
                        .groceryCode(product.getProductId())
                        .vendor(vendor)
                        .price(product.getPrc().getP())
                        .priceWithDiscount(product.getPrc().getS())
                        .priceWithLoyaltyCard(product.getPrc().getL())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                    grocery.setGroceryVendors(Set.of(groceryVendor));

                    groceries.add(grocery);
                }
            );

        return groceries;
    }
}
