package dev.petkevicius.groceryPriceChecker.service.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.controller.model.GroceryRequest;
import dev.petkevicius.groceryPriceChecker.controller.model.SortValue;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.VendorDTO;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public GroceryPageDTO getALlApprovedGroceries(
        CategoryType categoryType,
        GroceryRequest request
    ) {
        Pageable page = PageRequest.of(
            request.page().map(p-> p - 1).orElse(0),
            request.size().orElse(25),
            request.sortBy()
                .map(sortValue -> sortValue.equals(SortValue.PRICE_ASC)
                    ? Sort.by("groceryVendors.price").ascending()
                    : Sort.by("groceryVendors.price").descending()
                ).orElse(Sort.unsorted())
        );

        Page<Grocery> groceries = switch (categoryType) {
            case Category category ->
                groceryRepository.findByCategoryAndGroceryVendorsApprovedTrue(category, page);
            case Category.SubCategory subCategory ->
                groceryRepository.findBySubCategoryAndGroceryVendorsApprovedTrue(subCategory, page);
            case Category.SubSubCategory subSubCategory ->
                groceryRepository.findBySubSubCategoryAndGroceryVendorsApprovedTrue(subSubCategory, page);
            default -> throw new IllegalArgumentException("Invalid category type");
        };

        List<GroceryDTO> groceryDTOs = groceries.stream()
            .map(this::mapToGroceryDTO)
            .toList();

        return new GroceryPageDTO(
            groceryDTOs,
            groceries.getNumber() + 1,
            groceries.getTotalPages()
        );
    }

    public List<GroceryDTO> searchGroceries(String query) {
        return groceryRepository.findBySearchQuery(query).stream()
            .filter(grocery -> grocery.getGroceryVendors().stream().anyMatch(GroceryVendor::isApproved))
            .distinct()
            .map(this::mapToGroceryDTO)
            .toList();
    }

    private GroceryDTO mapToGroceryDTO(Grocery grocery) {
        return new GroceryDTO(
            grocery.getName(),
            grocery.getCountryOfOrigin(),
            grocery.getSupplier(),
            grocery.getCategory(),
            grocery.getSubCategory(),
            grocery.getQuantity(),
            grocery.getUnit(),
            grocery.getImageUrl(),
            grocery.getGroceryVendors().stream()
                .filter(GroceryVendor::isApproved)
                .map(groceryVendor -> new GroceryVendorDTO(
                    new VendorDTO(
                        groceryVendor.getVendor().getId(),
                        groceryVendor.getVendor().getImageUrl(),
                        groceryVendor.getVendor().getCountryOfOrigin()
                    ),
                    groceryVendor.getPrice(),
                    groceryVendor.getPriceWithDiscount(),
                    groceryVendor.getPriceWithLoyaltyCard()
                ))
                .toList()
        );
    }
}
