package dev.petkevicius.groceryPriceChecker.service.groceries;

import java.util.Comparator;
import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.VendorDTO;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public GroceryPageDTO getALlApprovedGroceries(
        CategoryType categoryType,
        Pageable pageable
    ) {
        Page<Grocery> groceries = groceryRepository.findApprovedGroceriesByCategory(categoryType, pageable);

        List<GroceryDTO> groceryDTOs = groceries.stream()
            .map(this::mapToGroceryDTO)
            .toList();

        return new GroceryPageDTO(
            groceryDTOs,
            groceries.getNumber(),
            groceries.getTotalPages()
        );
    }

    public List<CheapestVendorDTO> getCheapestCategoryGrocery(CategoryType categoryType) {
       return groceryRepository.findCheapestItemInSpecificCategory(categoryType);
    }

    public List<GroceryDTO> searchGroceries(String query) {
        return groceryRepository.findBySearchQuery(query).stream()
            .filter(grocery -> grocery.getGroceryVendors().stream().anyMatch(GroceryVendor::isApproved))
            .distinct()
            .map(this::mapToGroceryDTO)
            .toList();
    }

    public GroceryDTO mapToGroceryDTO(Grocery grocery) {
        return new GroceryDTO(
            grocery.getId(),
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
                .sorted(Comparator.comparing(GroceryVendor::getPrice))
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
