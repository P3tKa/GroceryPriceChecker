package dev.petkevicius.groceryPriceChecker.controller.model;

import java.util.Optional;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.RequestParam;

public record GroceryRequest(
    @RequestParam(name = "page", defaultValue = "")
    @Min(value = 0, message = "Page must be 0 or greater")
    Optional<Integer> page,

    @RequestParam(name = "size")
    @Size(max = 100, message = "Size must be between 0 and 100")
    Optional<Integer> size,

    @RequestParam(name = "sortBy")
    Optional<SortValue> sortBy
) {
}
