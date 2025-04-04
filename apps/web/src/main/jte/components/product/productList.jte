@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param List<GroceryDTO> groceries
@param ShoppingCartDTO shoppingCart

@if(groceries.isEmpty())
    <div class="flex flex-col justify-center items-center w-full relative">
        <h2>No groceries found</h2>
    </div>
@endif
<div id="grocery-list" class="grid gap-2 w-full max-w-3xl px-4 overflow-hidden p-4 pt-1"
     style="grid-template-columns: repeat(auto-fit, minmax(min(175px, 100%), 1fr));
        ">
    @for(GroceryDTO grocery : groceries)
        @template.components.product.product(grocery=grocery, shoppingCart=shoppingCart)
    @endfor
    @for(int i = groceries.size(); i < 4; i++)
        <div></div>
    @endfor
</div>