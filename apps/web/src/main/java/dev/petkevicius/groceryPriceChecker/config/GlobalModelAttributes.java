package dev.petkevicius.groceryPriceChecker.config;

import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        model.addAttribute("categories", Category.values());
    }

    @ModelAttribute
    public void addAuthenticationStatusToModel(Model model, Authentication authentication) {
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
    }
}
