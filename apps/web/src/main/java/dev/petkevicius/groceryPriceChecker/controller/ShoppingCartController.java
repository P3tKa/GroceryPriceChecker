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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add")
    public String addItemToBasket(
        @RequestParam String shoppingCartId,
        @RequestParam String groceryId,
        @RequestParam BigDecimal quantity,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.addGroceryToShoppingCart(shoppingCartId, groceryId, quantity);
        model.addAttribute("shoppingCart", shoppingCart);
        return "layout/cart";
    }

    @DeleteMapping("/{shoppingCartId}/remove")
    public String removeItemFromBasket(
        @PathVariable String shoppingCartId,
        @RequestParam String groceryId,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.removeItemFromBasket(shoppingCartId, groceryId);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("opened", true);
        return "layout/cart";
    }

    @PutMapping("/{shoppingCartId}/update")
    public String updateItemQuantity(
        @PathVariable String shoppingCartId,
        @RequestParam String groceryId,
        @RequestParam BigDecimal quantity,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.updateItemQuantity(shoppingCartId, groceryId, quantity);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("opened", true);
        return "layout/cart";
    }
}
