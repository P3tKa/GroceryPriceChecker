package dev.petkevicius.groceryPriceChecker.controller;

import java.util.List;

import dev.petkevicius.groceryPriceChecker.controller.model.GroceryRequest;
import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO;
import dev.petkevicius.groceryPriceChecker.service.groceries.GroceryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/")
    public String view(Model model) {
        List<GroceryDTO> groceries = groceryService.getALlApprovedGroceries();
        model.addAttribute("groceries", groceries);
        return "pages/index";
    }

    @GetMapping("/{category}")
    public String viewCategory(
        @PathVariable Category category,
        GroceryRequest request,
        Model model
    ) {
        List<GroceryDTO> groceries = groceryService.getALlApprovedGroceries(category, request);
        model.addAttribute("groceries", groceries);
        return "components/product/productList";
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
}