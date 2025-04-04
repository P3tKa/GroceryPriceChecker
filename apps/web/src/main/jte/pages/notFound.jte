@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param Category[] categories
@param ShoppingCartDTO shoppingCart

@template.layout.main(content = @`
    <div class="text-center content-center h-screen">
        <p>Error 404!</p>
        <p>Page not found</p>
    </div>
`, categories = categories, shoppingCart = shoppingCart)
