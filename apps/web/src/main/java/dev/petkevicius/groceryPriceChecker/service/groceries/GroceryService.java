package dev.petkevicius.groceryPriceChecker.service.groceries;

import java.util.List;
import java.util.Optional;

import dev.petkevicius.groceryPriceChecker.controller.model.GroceryRequest;
import dev.petkevicius.groceryPriceChecker.controller.model.SortValue;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.VendorDTO;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
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

    public List<GroceryDTO> getALlApprovedGroceries() {
        return getALlApprovedGroceries(
            Category.FRUITS_AND_VEGETABLES,
            new GroceryRequest(Optional.empty(), Optional.empty(), Optional.empty())
        );
    }
    public List<GroceryDTO> getALlApprovedGroceries(
        Category category,
        GroceryRequest request
    ) {
        Pageable page = PageRequest.of(
            request.page().orElse(0),
            request.size().orElse(25),
            request.sortBy()
                .map(sortValue -> sortValue.equals(SortValue.PRICE_ASC)
                    ? Sort.by("groceryVendors.price").ascending()
                    : Sort.by("groceryVendors.price").descending()
                ).orElse(Sort.unsorted())
        );

        return groceryRepository.findByCategoryAndGroceryVendorsApprovedTrue(category, page).stream()
            .map(this::mapToGroceryDTO)
            .toList();
    }

    public List<GroceryDTO> searchGroceries(String query) {
        return groceryRepository.findBySearchQuery(query).stream()
            .filter(grocery -> grocery.getGroceryVendors().stream().anyMatch(GroceryVendor::isApproved))
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
