@param java.util.List<dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO> groceries

<div class="grid gap-2 w-full max-w-3xl px-4"
     style="grid-template-columns: repeat(auto-fit, minmax(min(175px, 100%), 1fr));
        ">
    @for(dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO grocery : groceries)
        @template.components.product.product(grocery=grocery)
    @endfor
</div>