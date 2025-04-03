package dev.petkevicius.groceryPriceChecker.controller;

import java.util.List;

import javax.validation.Valid;

import dev.petkevicius.groceryPriceChecker.controller.model.GroceryRequest;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.CategoryType;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO;
import dev.petkevicius.groceryPriceChecker.service.groceries.GroceryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Validated
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/")
    public String view(Model model) {
        model.addAttribute("categories", Category.values());
        return "pages/index";
    }

    @GetMapping("/{category}")
    public String viewCategory(
        @PathVariable String category,
        @Valid GroceryRequest request,
        BindingResult bindingResult,
        Model model
    ) {
        model.addAttribute("categories", Category.values());

        if (bindingResult.hasErrors()) {
            model.addAttribute("groceries", List.of());
            return "pages/category";
        }

        CategoryType categoryType = getCategoryType(category);
        if (categoryType == null) {
            model.addAttribute("groceries", List.of());
            return "pages/notFound";
        }

        GroceryPageDTO groceries = groceryService.getALlApprovedGroceries(categoryType, request);
        model.addAttribute("groceries", groceries);
        model.addAttribute("groceryRequest", request);

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