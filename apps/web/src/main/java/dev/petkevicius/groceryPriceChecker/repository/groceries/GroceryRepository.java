package dev.petkevicius.groceryPriceChecker.repository.groceries;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, String> {
}
