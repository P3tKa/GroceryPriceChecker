package dev.petkevicius.groceryPriceChecker.service.cart;

import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
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
import java.util.Optional;

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
    public ShoppingCartDTO createShoppingCart(String userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        return mapToShoppingCartDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO addGroceryToShoppingCart(String basketId, String groceryId, BigDecimal quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(basketId).orElseThrow(() -> new IllegalArgumentException("Basket not found"));
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(() -> new IllegalArgumentException("Grocery not found"));

        Optional<ShoppingCartGrocery> existingItem = shoppingCartGroceryRepository.findByShoppingCartAndGrocery(shoppingCart, grocery);
        if (existingItem.isPresent()) {
            ShoppingCartGrocery basketItem = existingItem.get();
            basketItem.setQuantity(basketItem.getQuantity().add(quantity));
            shoppingCartGroceryRepository.save(basketItem);
        } else {
            ShoppingCartGrocery shoppingCartGrocery = new ShoppingCartGrocery();
            shoppingCartGrocery.setShoppingCart(shoppingCart);
            shoppingCartGrocery.setGrocery(grocery);
            shoppingCartGrocery.setQuantity(quantity);
            shoppingCartGroceryRepository.save(shoppingCartGrocery);
        }
        return mapToShoppingCartDTO(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO removeItemFromBasket(String basketId, String groceryId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(basketId).orElseThrow(() -> new IllegalArgumentException("Basket not found"));
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(() -> new IllegalArgumentException("Grocery not found"));

        shoppingCartGroceryRepository.deleteByShoppingCartAndGrocery(shoppingCart, grocery);
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

    private ShoppingCartDTO mapToShoppingCartDTO(ShoppingCart shoppingCart) {
        return new ShoppingCartDTO(
            shoppingCart.getId(),
            shoppingCart.getShoppingCartGroceries().stream()
                .map(grocery -> new ShoppingCartGroceryDTO (
                        grocery.getId(),
                        groceryService.mapToGroceryDTO(grocery.getGrocery()),
                        grocery.getQuantity()
                    ))
                .toList()
        );
    }
}