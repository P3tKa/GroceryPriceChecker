@param java.util.List<dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO> groceries
@param dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category[] categories

@template.layout.main(content = @`
    <div class="flex flex-col justify-center items-center w-full relative">
        <span id="loader-icon"></span>
        <div class="flex flex-col items-center w-full max-w-3xl">
            @template.components.filter.pagination()
            @template.components.filter.filter()
            @template.components.product.productList(groceries = groceries)
        </div>
    </div>
`, categories = categories)
