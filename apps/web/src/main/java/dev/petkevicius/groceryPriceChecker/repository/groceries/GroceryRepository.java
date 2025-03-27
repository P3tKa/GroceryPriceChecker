package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, String> {

    @Query(value = """
        SELECT g.*, ts_rank(g.search_vector, query) AS rank
        FROM groceries g
        JOIN groceries_vendors gv ON g.id = gv.grocery_id
        JOIN plainto_tsquery('lithuanian', :searchQuery) as query ON g.search_vector @@ query
        WHERE gv.approved = true
        ORDER BY rank DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Grocery> findBySearchQuery(@Param("searchQuery") String searchQuery);

    Page<Grocery> findByCategoryAndGroceryVendorsApprovedTrue(Category category, Pageable pageable);
}
