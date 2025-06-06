package dev.petkevicius.groceryPriceChecker.service.cart;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryVendor;
import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCart;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.ShoppingCartGrocery;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartGroceryDTO;
import dev.petkevicius.groceryPriceChecker.repository.groceries.GroceryRepository;
import dev.petkevicius.groceryPriceChecker.repository.groceries.ShoppingCartGroceryRepository;
import dev.petkevicius.groceryPriceChecker.repository.groceries.ShoppingCartRepository;
import dev.petkevicius.groceryPriceChecker.service.groceries.GroceryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final GroceryService groceryService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartGroceryRepository shoppingCartGroceryRepository;
    private final GroceryRepository groceryRepository;

    public ShoppingCartService(
        GroceryService groceryService,
        ShoppingCartRepository shoppingCartRepository,
        ShoppingCartGroceryRepository shoppingCartGroceryRepository,
        GroceryRepository groceryRepository
    ) {
        this.groceryService = groceryService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartGroceryRepository = shoppingCartGroceryRepository;
        this.groceryRepository = groceryRepository;
    }

    @Transactional
    public ShoppingCartDTO findUsersShoppingCart(String userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseGet(
            () -> {
                ShoppingCart newShoppingCart = new ShoppingCart();
                newShoppingCart.setId(UUID.randomUUID().toString());
                newShoppingCart.setUserId(userId);
                newShoppingCart.setCreatedAt(LocalDateTime.now());
                newShoppingCart.setUpdatedAt(LocalDateTime.now());
                return shoppingCartRepository.save(newShoppingCart);
            }
        );

        return mapToShoppingCartDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO addGroceryToShoppingCart(
        String userId,
        String shoppingCartId,
        String groceryId,
        BigDecimal quantity
    ) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseGet(() -> {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setId(shoppingCartId);
            newShoppingCart.setUserId(userId);
            newShoppingCart.setCreatedAt(LocalDateTime.now());
            newShoppingCart.setUpdatedAt(LocalDateTime.now());
            return shoppingCartRepository.save(newShoppingCart);
        });
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(() -> new IllegalArgumentException("Grocery not found"));

        Optional<ShoppingCartGrocery> existingItem = shoppingCartGroceryRepository.findByShoppingCartAndGrocery(shoppingCart, grocery);
        if (existingItem.isPresent()) {
            ShoppingCartGrocery shoppingCartGrocery = existingItem.get();
            shoppingCartGrocery.setQuantity(shoppingCartGrocery.getQuantity().add(quantity));
            shoppingCart.getShoppingCartGroceries().add(shoppingCartGrocery);
        } else {
            ShoppingCartGrocery shoppingCartGrocery = new ShoppingCartGrocery();
            shoppingCartGrocery.setId(UUID.randomUUID().toString());
            shoppingCartGrocery.setShoppingCart(shoppingCart);
            shoppingCartGrocery.setGrocery(grocery);
            shoppingCartGrocery.setQuantity(quantity);
            shoppingCart.getShoppingCartGroceries().add(shoppingCartGrocery);
        }
        shoppingCartRepository.save(shoppingCart);
        return mapToShoppingCartDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO chooseAlternativeGrocery(
        String userId,
        String shoppingCartId,
        String newGroceryId,
        String oldGroceryId,
        BigDecimal quantity
    ) {
        removeItemFromBasket(shoppingCartId, oldGroceryId);
        return addGroceryToShoppingCart(userId, shoppingCartId, newGroceryId, quantity);
    }

    @Transactional
    public ShoppingCartDTO removeItemFromBasket(String shoppingCartId, String groceryId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new IllegalArgumentException("Shopping cart not found"));
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(() -> new IllegalArgumentException("Grocery not found"));

        shoppingCart.getShoppingCartGroceries().stream()
            .filter(shoppingCartGrocery -> shoppingCartGrocery.getGrocery().getId().equals(groceryId))
            .findFirst()
            .ifPresent(shoppingCartGrocery -> shoppingCart.getShoppingCartGroceries().remove(shoppingCartGrocery));

        if (shoppingCart.getShoppingCartGroceries().isEmpty()) {
            shoppingCartRepository.delete(shoppingCart);
        } else {
            shoppingCartRepository.save(shoppingCart);
        }

        return mapToShoppingCartDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO updateItemQuantity(String basketId, String groceryId, BigDecimal quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(basketId).orElseThrow(() -> new IllegalArgumentException("Basket not found"));
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(() -> new IllegalArgumentException("Grocery not found"));

        ShoppingCartGrocery basketItem = shoppingCartGroceryRepository.findByShoppingCartAndGrocery(shoppingCart, grocery)
            .orElseThrow(() -> new IllegalArgumentException("Item not found in basket"));

        basketItem.setQuantity(quantity);
        shoppingCartGroceryRepository.save(basketItem);
        return mapToShoppingCartDTO(shoppingCart);
    }

    public BigDecimal getTotalPriceSaved() {
        return shoppingCartRepository.getTotalPriceFromAllShoppingCarts();
    }

    private ShoppingCartDTO mapToShoppingCartDTO(ShoppingCart shoppingCart) {
        return new ShoppingCartDTO(
            shoppingCart.getId(),
            getMostPopularCheapestVendor(shoppingCart),
            shoppingCart.getShoppingCartGroceries().stream()
                .map(grocery -> new ShoppingCartGroceryDTO (
                        grocery.getId(),
                        groceryService.mapToGroceryDTO(grocery.getGrocery()),
                        grocery.getQuantity()
                    ))
                .toList()
        );
    }

    private VendorName getMostPopularCheapestVendor(ShoppingCart shoppingCart) {
        return shoppingCart.getShoppingCartGroceries().stream()
            .map(grocery -> grocery.getGrocery().getGroceryVendors().stream()
                .min(Comparator.comparing(GroceryVendor::getPrice)) // Select the vendor with the cheapest price
                .orElseThrow(() -> new IllegalArgumentException("No vendors available for grocery"))
                .getVendor().getId())
            .collect(Collectors.groupingBy(vendor -> vendor, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}