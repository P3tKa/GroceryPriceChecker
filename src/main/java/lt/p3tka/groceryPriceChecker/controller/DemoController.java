package lt.p3tka.groceryPriceChecker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    private int visitorCount = 0;

    @GetMapping("/")
    public String view(Model model) {
        visitorCount++;
        model.addAttribute("model", new Message("World", visitorCount));
        return "index";
    }

    public record Message(String message, int visitorCount) {
    }
}