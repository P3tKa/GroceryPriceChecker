@param java.util.List<dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO> groceries

@template.layout.main(content = @`
    <div class="flex flex-col justify-center items-center h-screen w-full">
        @template.components.product.productList(groceries = groceries)
    </div>
`)
