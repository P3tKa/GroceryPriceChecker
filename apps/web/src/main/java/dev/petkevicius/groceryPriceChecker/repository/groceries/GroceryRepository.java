package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
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

    @Query(value = """
            SELECT *
            FROM (
                SELECT DISTINCT ON (g.id) g.*, gv.price as price
                FROM groceries g
                JOIN groceries_vendors gv ON g.id = gv.grocery_id
                WHERE gv.approved = true
                AND (
                    CASE
                        WHEN :categoryType = 'category' THEN g.category
                        WHEN :categoryType = 'subCategory' THEN g.sub_category
                        WHEN :categoryType = 'subSubCategory' THEN g.sub_sub_category
                    END = :categoryValue
                )
                ORDER BY g.id, gv.price
            ) sub
        """,
        countQuery = """
            SELECT COUNT(DISTINCT g.id)
            FROM groceries g
            JOIN groceries_vendors gv ON g.id = gv.grocery_id
            WHERE gv.approved = true
            AND (
                CASE
                    WHEN :categoryType = 'category' THEN g.category
                    WHEN :categoryType = 'subCategory' THEN g.sub_category
                    WHEN :categoryType = 'subSubCategory' THEN g.sub_sub_category
                END = :categoryValue
            )
        """,
        nativeQuery = true)
    Page<Grocery> findGroceriesWithApprovedVendors(
        @Param("categoryType") String categoryType,
        @Param("categoryValue") String categoryValue,
        Pageable pageable
    );

    default Page<Grocery> findApprovedGroceriesByCategory(CategoryType categoryType, Pageable pageable) {
        return switch (categoryType) {
            case Category category ->
                findGroceriesWithApprovedVendors("category", category.name(), pageable);
            case Category.SubCategory subCategory ->
                findGroceriesWithApprovedVendors("subCategory", subCategory.name(), pageable);
            case Category.SubSubCategory subSubCategory ->
                findGroceriesWithApprovedVendors("subSubCategory", subSubCategory.name(), pageable);
        };
    }
}
