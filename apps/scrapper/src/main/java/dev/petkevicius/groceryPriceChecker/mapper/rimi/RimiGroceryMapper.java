package dev.petkevicius.groceryPriceChecker.mapper.rimi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryUnit;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Vendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.mapper.GroceryMapper;
import dev.petkevicius.groceryPriceChecker.service.rimi.RimiConstants;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class RimiGroceryMapper extends GroceryMapper {

    public List<Grocery> mapToGrocery(Elements responseBody, CategoryType categoryType) {
        List<Grocery> groceries = new ArrayList<>();

        Vendor vendor = entityManager.getReference(Vendor.class, RimiConstants.VENDOR_NAME);

        responseBody.forEach(product -> {
            Element productContainer = Optional.ofNullable(product.selectFirst("div.js-product-container.card")).orElseThrow();

            String groceryCode = productContainer.attr("data-product-code");

            Element imageWrapper = Optional.ofNullable(productContainer.selectFirst("div.card__image-wrapper")).orElseThrow();

            String imageUrl = Optional.ofNullable(imageWrapper.selectFirst("img"))
                .map(element -> element.attr("src"))
                .orElseThrow();

            BigDecimal priceWithLoyalty = Optional.ofNullable(imageWrapper.selectFirst("div.price-label__price"))
                .map(loyaltyPriceLabel -> extractPriceFromHtmlElement(loyaltyPriceLabel, "span.major", "span.cents"))
                .orElse(null);

            Element productInfo = Optional.ofNullable(product.selectFirst("div.card__details")).orElseThrow();

            String name = extractValueFromHtmlElement(productInfo.selectFirst("p.card__name"));

            Element productDetails = Optional.ofNullable(productInfo.selectFirst("div.card__price-wrapper")).orElseThrow();

            Element pricePerElement = productDetails.selectFirst("p.card__price-per");
            if (pricePerElement == null || !pricePerElement.text().matches(".*\\d.*")) {
                return;
            }

            BigDecimal pricePerUnit = Optional.ofNullable(productDetails.selectFirst("p.card__price-per"))
                    .map(this::getPriceFromHtmlElement)
                    .orElseThrow();

            GroceryUnit unit = GroceryUnit.fromString(
                extractValueFromHtmlElement(productDetails.selectFirst("p.card__price-per"))
                    .replaceAll("[^a-zA-Z]", "")
                    .trim()
            );

            BigDecimal price;
            BigDecimal priceWithDiscount;

            if (productDetails.hasClass("-has-discount")) {
                price = Optional.ofNullable(productDetails.selectFirst("div.old-price-tag span"))
                    .map(this::getPriceFromHtmlElement)
                    .orElseThrow();

                priceWithDiscount = Optional.ofNullable(productDetails.selectFirst("div.price-tag"))
                    .map(discountPriceLabel -> extractPriceFromHtmlElement(discountPriceLabel, "span", "div sup"))
                    .orElseThrow();
            } else {
                price = Optional.ofNullable(productDetails.selectFirst("div.price-tag"))
                    .map(priceLabel -> extractPriceFromHtmlElement(priceLabel, "span", "div sup"))
                    .orElseThrow();

                priceWithDiscount = null;
            }

            BigDecimal quantity = (priceWithDiscount != null)
                ? priceWithDiscount.divide(pricePerUnit, 3, RoundingMode.HALF_DOWN)
                : price.divide(pricePerUnit, 3, RoundingMode.HALF_DOWN);

            Grocery grocery = Grocery.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .countryOfOrigin(null) // Can't get from current RIMI response
                .supplier(null) // Can't get from current RIMI response
                .quantity(quantity)
                .unit(unit)
                .imageUrl(imageUrl)
                .build();

            setCategoryFields(grocery, categoryType);

            GroceryVendor groceryVendor = GroceryVendor.builder()
                .id(UUID.randomUUID().toString())
                .grocery(grocery)
                .groceryCode(groceryCode)
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

    private BigDecimal extractPriceFromHtmlElement(Element baseElement, String majorElementSelector, String minorElementSelector) {
        return Optional.ofNullable(baseElement)
            .map(element -> {
                String euros = Optional.ofNullable(element.selectFirst(majorElementSelector))
                    .map(Element::text).orElse(null);
                String cents = Optional.ofNullable(element.selectFirst(minorElementSelector))
                    .map(Element::text).orElse(null);

                if (euros == null || cents == null) {
                    return null;
                }

                return new BigDecimal(euros + "." + cents);
            })
            .orElse(null);
    }

    private BigDecimal getPriceFromHtmlElement(Element element) {
        return Optional.ofNullable(element)
            .map(Element::text)
            .map(p -> p
                .replaceAll("[^\\d,]", "")
                .replace(",", ".")
            )
            .map(BigDecimal::new)
            .orElse(null);
    }

    private String extractValueFromHtmlElement(Element element) {
        return Optional.ofNullable(element)
            .map(Element::text)
            .orElse(null);
    }
}

