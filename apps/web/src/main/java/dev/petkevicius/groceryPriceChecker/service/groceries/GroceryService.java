package dev.petkevicius.groceryPriceChecker.service.groceries;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.VendorDTO;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import org.springframework.stereotype.Service;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public List<GroceryDTO> getALlApprovedGroceries() {
        return groceryRepository.findAll().stream()
            .filter(grocery -> grocery.getGroceryVendors().stream().anyMatch(GroceryVendor::isApproved))
            .map(this::mapToGroceryDTO)
            .toList();
    }

    public List<Grocery> searchGroceries(String query) {
        return groceryRepository.findBySearchQuery(query);
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
