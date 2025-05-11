package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.math.BigDecimal;
import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, String> {

    @Query(value = """
        SELECT *
        FROM groceries
        WHERE category = :categoryId
            AND unit = :unit
            AND quantity BETWEEN :quantity - 0.05 AND :quantity + 0.05
            AND similarity(name, :inputName) > 0.7
        ORDER BY similarity(name, :inputName) DESC
        LIMIT 1
        """, nativeQuery = true)
    Optional<Grocery> findMatchingProduct(
        @Param("inputName") String inputName,
        @Param("categoryId") String categoryId,
        @Param("unit") String unit,
        @Param("quantity") BigDecimal quantity
    );
}
