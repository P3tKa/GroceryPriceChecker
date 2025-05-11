@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryPageDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO
@import org.springframework.data.domain.Pageable

@param GroceryPageDTO groceries
@param Category[] categories
@param Pageable pageable
@param ShoppingCartDTO shoppingCart
@param List<CheapestVendorDTO> cheapestVendorsByCategory
@param boolean isAuthenticated

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
            @template.components.product.productList(
                groceries = groceries.groceries(),
                shoppingCart = shoppingCart,
                isAuthenticated = isAuthenticated,
                cheapestVendorsByCategory = cheapestVendorsByCategory
            )
        </div>
    </div>
`, categories = categories, isAuthenticated = isAuthenticated)
