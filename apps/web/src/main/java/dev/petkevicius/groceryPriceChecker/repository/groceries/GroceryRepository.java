package dev.petkevicius.groceryPriceChecker.repository.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO;
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
        AND (:vendorId IS NULL OR gv.vendor_id = :vendorId)
        ORDER BY rank DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Grocery> findBySearchQuery(
        @Param("searchQuery") String searchQuery,
        @Param("vendorId") String vendorId
    );

    @Query(value = """
        SELECT gv.vendor_id AS name, v.image_url as imageUrl, AVG(gv.price / g.quantity) AS averagePrice
        FROM groceries g
        JOIN groceries_vendors gv ON g.id = gv.grocery_id
        JOIN vendors v ON gv.vendor_id = v.id
        WHERE gv.approved = true
        AND (
            CASE
                WHEN :categoryType = 'category' THEN g.category
                WHEN :categoryType = 'subCategory' THEN g.sub_category
                WHEN :categoryType = 'subSubCategory' THEN g.sub_sub_category
            END = :categoryValue
        )
        GROUP BY gv.vendor_id, v.image_url
        ORDER BY averagePrice ASC
        """, nativeQuery = true)
    List<CheapestVendorDTO> findCheapestItemInSpecificCategory(
        @Param("categoryType") String categoryType,
        @Param("categoryValue") String categoryValue
    );

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

    default List<CheapestVendorDTO> findCheapestItemInSpecificCategory(CategoryType categoryType) {
        return switch (categoryType) {
            case Category category ->
                findCheapestItemInSpecificCategory("category", category.name());
            case Category.SubCategory subCategory ->
                findCheapestItemInSpecificCategory("subCategory", subCategory.name());
            case Category.SubSubCategory subSubCategory ->
                findCheapestItemInSpecificCategory("subSubCategory", subSubCategory.name());
        };
    }

    default List<Grocery> findBySearchQuery(String searchQuery) {
        return findBySearchQuery(searchQuery, null);
    }
}
