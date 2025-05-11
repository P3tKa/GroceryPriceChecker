package dev.petkevicius.groceryPriceChecker.service.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryPriceHistory;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Vendor;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryVendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroceryService {

    private final GroceryNameService groceryNameService;
    private final GroceryRepository groceryRepository;
    private final GroceryVendorRepository groceryVendorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public GroceryService(
        GroceryNameService groceryNameService,
        GroceryRepository groceryRepository,
        GroceryVendorRepository groceryVendorRepository
    ) {
        this.groceryNameService = groceryNameService;
        this.groceryRepository = groceryRepository;
        this.groceryVendorRepository = groceryVendorRepository;
    }

    @Transactional
    public void saveOrUpdateGroceries(List<Grocery> groceries) {
        List<Grocery> newGroceries = new ArrayList<>();
        List<Grocery> existingGroceries = new ArrayList<>();
        List<GroceryVendor> updatedVendors = new ArrayList<>();

        groceries.forEach(grocery -> {
            String cleanedName = groceryNameService.stripSuffixes(grocery.getName());
            grocery.setName(cleanedName);

            grocery.getGroceryVendors().forEach(vendor -> {
                groceryVendorRepository.findByVendorAndGroceryCodeAndGrocery_Category(
                    vendor.getVendor(),
                    vendor.getGroceryCode(),
                    vendor.getGrocery().getCategory()
                ).ifPresentOrElse(existingVendorProduct -> {
                        if (hasPriceChanged(existingVendorProduct.getPrice(), vendor.getPrice()) ||
                            hasPriceChanged(existingVendorProduct.getPriceWithDiscount(), vendor.getPriceWithDiscount()) ||
                            hasPriceChanged(existingVendorProduct.getPriceWithLoyaltyCard(), vendor.getPriceWithLoyaltyCard())
                        ) {
                            updateExistingVendorProduct(existingVendorProduct, vendor);
                            updatedVendors.add(existingVendorProduct);
                        }
                    }, () -> groceryRepository.findMatchingProduct(
                            grocery.getName(),
                            grocery.getCategory().name(),
                            grocery.getUnit().name(),
                            grocery.getQuantity()
                    ).ifPresentOrElse(
                        existingGrocery -> {
                            addAdditionalVendorToGrocery(existingGrocery, grocery.getGroceryVendors());
                            existingGroceries.add(existingGrocery);
                        },
                        () -> newGroceries.add(grocery)
                    )
                );
            });
        });

        groceryRepository.saveAll(newGroceries);
        groceryRepository.saveAll(existingGroceries);
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

    private void addAdditionalVendorToGrocery(Grocery grocery, Set<GroceryVendor> newVendors) {
        Set<GroceryVendor> vendors = newVendors.stream()
            .map(newVendor -> GroceryVendor.builder()
                .id(UUID.randomUUID().toString())
                .grocery(grocery)
                .vendor(entityManager.find(Vendor.class, newVendor.getVendor().getId()))
                .groceryCode(newVendor.getGroceryCode())
                .price(newVendor.getPrice())
                .priceWithDiscount(newVendor.getPriceWithDiscount())
                .priceWithLoyaltyCard(newVendor.getPriceWithLoyaltyCard())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .approved(false)
                .build())
            .collect(Collectors.toSet());
        grocery.getGroceryVendors().addAll(vendors);
    }

    private boolean hasPriceChanged(BigDecimal oldPrice, BigDecimal newPrice) {
        if (oldPrice == null && newPrice == null) {
            return false;
        }
        if (oldPrice == null || newPrice == null) {
            return true;
        }

        return oldPrice.compareTo(newPrice) != 0;
    }
}
