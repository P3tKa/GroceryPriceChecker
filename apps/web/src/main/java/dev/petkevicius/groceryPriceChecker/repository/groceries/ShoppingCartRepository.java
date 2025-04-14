package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.math.BigDecimal;
import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    Optional<ShoppingCart> findByUserId(String userId);

    @Query(value = """
        WITH vendor_prices AS (
            SELECT
                gv.grocery_id,
                MIN(gv.price) AS min_price,
                MAX(gv.price) AS max_price,
                COUNT(DISTINCT gv.vendor_id) AS vendor_count
            FROM groceries_vendors gv
            GROUP BY gv.grocery_id
        ),
             cart_groceries AS (
                 SELECT
                     scg.shopping_cart_id,
                     scg.grocery_id,
                     vp.vendor_count,
                     (vp.max_price - vp.min_price) AS price_difference
                 FROM shopping_cart_groceries scg
                          JOIN vendor_prices vp
                               ON scg.grocery_id = vp.grocery_id
             )
        SELECT
            SUM(cg.price_difference) AS total_price_difference
        FROM cart_groceries cg
        WHERE cg.vendor_count > 1;
        """, nativeQuery = true)
    BigDecimal getTotalPriceFromAllShoppingCarts();
}
