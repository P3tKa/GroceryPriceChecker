@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category

@param Category[] categories
@param boolean isAuthenticated

@template.layout.main(content = @`
    <div class="text-center content-center h-screen">
        <p>Error 404!</p>
        <p>Page not found</p>
    </div>
`, categories = categories, isAuthenticated = isAuthenticated)
