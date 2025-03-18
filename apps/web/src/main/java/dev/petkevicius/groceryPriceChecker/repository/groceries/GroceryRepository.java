package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, String> {

    @Query(value = """
        SELECT *, ts_rank(search_vector, query) AS rank
        FROM groceries, plainto_tsquery('lithuanian', :searchQuery) as query
        WHERE search_vector @@ query
        ORDER BY rank DESC
        LIMIT 5
        """, nativeQuery = true)
    List<Grocery> findBySearchQuery(@Param("searchQuery") String searchQuery);
}
