package dev.petkevicius.groceryPriceChecker.controller;

import java.math.BigDecimal;

import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO;
import dev.petkevicius.groceryPriceChecker.service.cart.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/create")
    public String createShoppingBasket(
        @RequestParam String userId,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.createShoppingCart(userId);
        model.addAttribute("shoppingCart", shoppingCart);
        return "components/cart/cart";
    }

    @PostMapping("/{basketId}/add")
    public String addItemToBasket(
        @PathVariable String basketId,
        @RequestParam String groceryId,
        @RequestParam BigDecimal quantity,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.addGroceryToShoppingCart(basketId, groceryId, quantity);
        model.addAttribute("shoppingCart", shoppingCart);
        return "components/cart/cart";
    }

    @DeleteMapping("/{basketId}/remove")
    public String removeItemFromBasket(
        @PathVariable String basketId,
        @RequestParam String groceryId,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.removeItemFromBasket(basketId, groceryId);
        model.addAttribute("shoppingCart", shoppingCart);
        return "components/cart/cart";
    }

    @PutMapping("/{basketId}/update")
    public String updateItemQuantity(
        @PathVariable String basketId,
        @RequestParam String groceryId,
        @RequestParam BigDecimal quantity,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.updateItemQuantity(basketId, groceryId, quantity);
        model.addAttribute("shoppingCart", shoppingCart);
        return "components/cart/cart";
    }
}
