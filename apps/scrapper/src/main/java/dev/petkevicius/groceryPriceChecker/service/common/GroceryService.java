package dev.petkevicius.groceryPriceChecker.service.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryPriceHistory;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryVendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;
    private final GroceryVendorRepository groceryVendorRepository;

    public GroceryService(
        GroceryRepository groceryRepository,
        GroceryVendorRepository groceryVendorRepository
    ) {
        this.groceryRepository = groceryRepository;
        this.groceryVendorRepository = groceryVendorRepository;
    }

    @Transactional
    public void saveOrUpdateGroceries(List<Grocery> groceries) {
        List<Grocery> newGroceries = new ArrayList<>();
        List<GroceryVendor> updatedVendors = new ArrayList<>();

        groceries.forEach(grocery -> {
            grocery.getGroceryVendors().forEach(vendor -> {
                groceryVendorRepository.findByVendorAndGroceryCode(vendor.getVendor(), vendor.getGroceryCode())
                    .ifPresentOrElse(existingVendorProduct -> {
                        if (!existingVendorProduct.getPrice().equals(vendor.getPrice())) {
                            updateExistingVendorProduct(existingVendorProduct, vendor);
                            updatedVendors.add(existingVendorProduct);
                        }
                    }, () -> newGroceries.add(grocery)); // TODO: if vendor is not found try to find same product on different vendor
            });
        });

        groceryRepository.saveAll(newGroceries);
        groceryVendorRepository.saveAll(updatedVendors);
    }

    private void updateExistingVendorProduct(GroceryVendor existingVendor, GroceryVendor newVendor) {
        GroceryPriceHistory priceHistory = GroceryPriceHistory.builder()
            .groceryVendor(existingVendor)
            .price(existingVendor.getPrice())
            .priceWithDiscount(existingVendor.getPriceWithDiscount())
            .priceWithLoyaltyCard(existingVendor.getPriceWithLoyaltyCard())
            .createdAt(existingVendor.getUpdatedAt())
            .build();

        existingVendor.getGroceryPriceHistories().add(priceHistory);
        existingVendor.setPrice(newVendor.getPrice());
        existingVendor.setPriceWithDiscount(newVendor.getPriceWithDiscount());
        existingVendor.setPriceWithLoyaltyCard(newVendor.getPriceWithLoyaltyCard());
        existingVendor.setUpdatedAt(LocalDateTime.now());
    }
}
