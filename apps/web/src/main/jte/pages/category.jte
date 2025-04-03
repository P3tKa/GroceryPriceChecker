@param dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO groceries
@param dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category[] categories
@param org.springframework.data.domain.Pageable pageable

@template.layout.main(content = @`
    <div class="flex flex-col justify-center items-center w-full relative">
        <span id="loader-icon"></span>
        <div class="flex flex-col items-center w-full max-w-3xl">
            <form
                    class="w-full max-w-3xl"
                    hx-get="javascript:window.location.href"
                    hx-target="#grocery-list"
            >
                @template.components.filter.pagination(currentPage=groceries.currentPage(), totalPages=groceries.totalPages())
                @template.components.filter.filter(pageable = pageable)
            </form>
            @template.components.product.productList(groceries = groceries.groceries())
        </div>
    </div>
`, categories = categories)
