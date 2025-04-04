package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCart;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCartGrocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartGroceryRepository extends JpaRepository<ShoppingCartGrocery, String> {
    Optional<ShoppingCartGrocery> findByShoppingCartAndGrocery(
        ShoppingCart shoppingCart,
        Grocery grocery
    );
}
