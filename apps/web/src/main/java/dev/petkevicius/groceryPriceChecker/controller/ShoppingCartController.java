package dev.petkevicius.groceryPriceChecker.controller;

import static dev.petkevicius.groceryPriceChecker.service.auth.AuthUtils.getUserId;

import java.math.BigDecimal;

import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO;
import dev.petkevicius.groceryPriceChecker.service.cart.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public String getShoppingCart(
        Authentication authentication,
        Model model
    ) {
        String userId = getUserId(authentication);
        if (userId.isEmpty()) {
            model.addAttribute("shoppingCart", null);
            model.addAttribute("opened", true);
            model.addAttribute("error", "UNAUTHENTICATED_USER");
            return "layout/cart";
        }

        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart(userId);
        model.addAttribute("opened", true);
        model.addAttribute("shoppingCart", shoppingCart);
        return "layout/cart";
    }

    @GetMapping("/pdf")
    public String getShoppingCartAsPdf(
        Authentication authentication,
        Model model
    ) {
        String userId = getUserId(authentication);
        if (userId.isEmpty()) {
            model.addAttribute("shoppingCart", null);
            return "components/cart/cart-pdf";
        }

        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart(userId);
        model.addAttribute("shoppingCart", shoppingCart);
        return "components/cart/cart-pdf";
    }

    @PostMapping("/add")
    public String addItemToBasket(
        @RequestParam String shoppingCartId,
        @RequestParam String groceryId,
        @RequestParam BigDecimal quantity,
        Authentication authentication,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.addGroceryToShoppingCart(getUserId(authentication), shoppingCartId, groceryId, quantity);
        model.addAttribute("shoppingCart", shoppingCart);
        return "layout/cart";
    }

    @PutMapping("/add")
    public String chooseAlternativeItem(
        @RequestParam String shoppingCartId,
        @RequestParam String newGroceryId,
        @RequestParam String oldGroceryId,
        @RequestParam BigDecimal quantity,
        Authentication authentication,
        Model model
    ) {
        ShoppingCartDTO shoppingCart = shoppingCartService.chooseAlternativeGrocery(
            getUserId(authentication),
            shoppingCartId,
            newGroceryId,
            oldGroceryId,
            quantity
        );
        model.addAttribute("opened", true);
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
