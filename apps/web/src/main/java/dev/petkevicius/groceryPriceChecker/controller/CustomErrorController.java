package dev.petkevicius.groceryPriceChecker.controller;

import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        // TODO: handle different error codes differently
        model.addAttribute("categories", Category.values());
        return "pages/notFound";
    }
}
