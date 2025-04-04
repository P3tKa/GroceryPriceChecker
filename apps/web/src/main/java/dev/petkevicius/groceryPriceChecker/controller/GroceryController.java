package dev.petkevicius.groceryPriceChecker.controller;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO;
import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO;
import dev.petkevicius.groceryPriceChecker.service.cart.ShoppingCartService;
import dev.petkevicius.groceryPriceChecker.service.groceries.GroceryService;
import org.springframework.data.domain.Pageable;
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
    public String view(Model model) {
        model.addAttribute("categories", Category.values());
        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart("testUserId");
        model.addAttribute("shoppingCart", shoppingCart);
        return "pages/index";
    }

    @GetMapping("/{category}")
    public String viewCategory(
        @PathVariable String category,
        Pageable pageable,
        Model model
    ) {
        model.addAttribute("categories", Category.values());

        CategoryType categoryType = getCategoryType(category);
        if (categoryType == null) {
            model.addAttribute("groceries", List.of());
            ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart("testUserId");
            model.addAttribute("shoppingCart", shoppingCart);

            return "pages/notFound";
        }

        GroceryPageDTO groceries = groceryService.getALlApprovedGroceries(categoryType, pageable);
        model.addAttribute("groceries", groceries);
        model.addAttribute("pageable", pageable);

        ShoppingCartDTO shoppingCart = shoppingCartService.findUsersShoppingCart("testUserId");
        model.addAttribute("shoppingCart", shoppingCart);

        return "pages/category";
    }

    @GetMapping("/search")
    public String search(
        @RequestParam String query,
        Model model
    ) {
        List<GroceryDTO> groceries = groceryService.searchGroceries(query);
        model.addAttribute("groceries", groceries);
        return "components/product/productList";
    }

    private CategoryType getCategoryType(String category) {
        try {
            return Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
        }

        try {
            return Category.SubCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
        }

        try {
            return Category.SubSubCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
        }

        return null;
    }

}