@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category

@param Category[] categories
@param List<GroceryDTO> groceries

@template.layout.main(content = @`
    @template.components.cart.cart(groceries = groceries)
`, categories = categories)
