package dev.petkevicius.groceryPriceChecker.repository.groceries;

import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
}
