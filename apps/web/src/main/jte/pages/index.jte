@import java.math.BigDecimal
@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category

@param Category[] categories
@param boolean isAuthenticated
@param BigDecimal totalPriceSaved

@template.layout.main(content = @`
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2 class="text-2xl font-semibold text-gray-800">Start by selecting a category</h2>
        <p class="text-lg font-bold mt-4 text-gray-600">
            Total amount saved across all users:
            <span class="text-3xl text-green-500 font-extrabold">
                @if(totalPriceSaved != null)
                    €${totalPriceSaved.setScale(2, java.math.RoundingMode.HALF_UP)}
                @endif
            </span>
        </p>
        <div id="grocery-list"></div>
    </div>
`, categories = categories, isAuthenticated = isAuthenticated)