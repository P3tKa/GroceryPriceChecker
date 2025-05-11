@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param List<GroceryDTO> groceries
@param ShoppingCartDTO shoppingCart
@param boolean isAuthenticated
@param List<CheapestVendorDTO> cheapestVendorsByCategory

@if(groceries.isEmpty())
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2>No groceries found</h2>
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
        @template.components.product.product(grocery=grocery, shoppingCart=shoppingCart, isAuthenticated=isAuthenticated)
    @endfor
    @for(int i = groceries.size(); i < 4; i++)
        <div></div>
    @endfor
</div>