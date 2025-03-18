package dev.petkevicius.groceryPriceChecker.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import org.springframework.stereotype.Component;

@Component
public class GroceryMapper {

    @PersistenceContext
    protected EntityManager entityManager;

    protected void setCategoryFields(Grocery grocery, CategoryType categoryType) {
        switch (categoryType) {
            case Category category -> grocery.setCategory(category);
            case Category.SubCategory subCategory -> {
                grocery.setSubCategory(subCategory);
                grocery.setCategory(Category.getCategory(subCategory));
            }
            case Category.SubSubCategory subSubCategory -> {
                grocery.setSubSubCategory(subSubCategory);
                grocery.setSubCategory(Category.getSubCategory(subSubCategory));
                grocery.setCategory(Category.getCategory(subSubCategory));
            }
            default -> throw new IllegalArgumentException("Unknown category type: " + categoryType);
        }
    }
}
