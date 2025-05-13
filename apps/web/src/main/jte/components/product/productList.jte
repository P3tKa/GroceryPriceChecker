@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param List<GroceryDTO> groceries
@param ShoppingCartDTO shoppingCart
@param boolean isAuthenticated
@param List<CheapestVendorDTO> cheapestVendorsByCategory
@param boolean isAlternativeGrocerySuggestion = false
@param String groceryToChangeId = null

@if(groceries.isEmpty())
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2>No groceries found</h2>
    </div>
@endif

@if(isAlternativeGrocerySuggestion)
    <button type="button"
            class="relative -m-2 p-8 text-gray-400 hover:text-gray-500 cursor-pointer"
            hx-trigger="click"
            hx-swap="none"
            hx-vals='{"toggle": "true"}'
            onclick="toggleAlternativeProductPanel()"
    >
        <span class="absolute -inset-0.5"></span>
        <span class="sr-only">Close panel</span>
        <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
        </svg>
    </button>
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2 class="text-2xl font-semibold text-gray-800">Alternative grocery suggestion</h2>
    </div>
@endif
@if(cheapestVendorsByCategory != null && !cheapestVendorsByCategory.isEmpty())
    <div class="relative right-95 bottom-19" >
        @template.components.chart.VendorRating(cheapestVendorsByCategory = cheapestVendorsByCategory)
    </div>
@endif
<div id="grocery-list" class="grid gap-2 w-full max-w-3xl px-4 overflow-hidden p-4 pt-1"
     style="grid-template-columns: repeat(auto-fit, minmax(min(175px, 100%), 1fr));
        ">
    @for(GroceryDTO grocery : groceries)
        @template.components.product.product(
            grocery=grocery,
            shoppingCart=shoppingCart,
            isAuthenticated=isAuthenticated,
            isAlternativeGrocerySuggestion=isAlternativeGrocerySuggestion,
            groceryToChangeId=groceryToChangeId
        )
    @endfor
    @for(int i = groceries.size(); i < 4; i++)
        <div></div>
    @endfor
</div>