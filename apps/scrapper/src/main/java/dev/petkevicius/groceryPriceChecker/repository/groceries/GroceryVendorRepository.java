package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Vendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryVendorRepository extends JpaRepository<GroceryVendor, String> {
    Optional<GroceryVendor> findByVendorAndGroceryCodeAndGrocery_Category(Vendor vendor, String groceryCode, Category category);
}