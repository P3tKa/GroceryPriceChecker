package dev.petkevicius.groceryPriceChecker.controller;

import static dev.petkevicius.groceryPriceChecker.service.auth.AuthUtils.getUserId;

import java.math.BigDecimal;
import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO;
import dev.petkevicius.groceryPriceChecker.service.cart.ShoppingCartService;
import dev.petkevicius.groceryPriceChecker.service.groceries.GroceryService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroceryController {

    private final GroceryService groceryService;
    private final ShoppingCartService shoppingCartService;

    public GroceryController(
        GroceryService groceryService,
        ShoppingCartService shoppingCartService
    ) {
        this.groceryService = groceryService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/")
    public String view(
        Model model
    ) {
        BigDecimal totalPriceSaved = shoppingCartService.getTotalPriceSaved();
        model.addAttribute("totalPriceSaved", totalPriceSaved);

        return "pages/index";
    }

    @GetMapping("/{category}")
    public String viewCategory(
        @PathVariable String category,
        Pageable pageable,
        Model model,
        Authentication authentication
    ) {
        String userId = getUserId(authentication);

        CategoryType categoryType = CategoryType.fromString(category);

        GroceryPageDTO groceries = groceryService.getALlApprovedGroceries(categoryType, pageable);
        model.addAttribute("groceries", groceries);
        model.addAttribute("pageable", pageable);

        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart(userId);
        model.addAttribute("shoppingCart", shoppingCart);

        return "pages/category";
    }

    @GetMapping("/search")
    public String search(
        @RequestParam String query,
        Model model,
        Authentication authentication
    ) {
        String userId = getUserId(authentication);

        List<GroceryDTO> groceries = groceryService.searchGroceries(query);
        model.addAttribute("groceries", groceries);

        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart(userId);
        model.addAttribute("shoppingCart", shoppingCart);

        return "components/product/productList";
    }

}