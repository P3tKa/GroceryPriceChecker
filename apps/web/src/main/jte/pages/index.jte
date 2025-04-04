@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param Category[] categories
@param ShoppingCartDTO shoppingCart

@template.layout.main(content = @`
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2>Start by selecting a category</h2>
    </div>
`, categories = categories, shoppingCart = shoppingCart)
