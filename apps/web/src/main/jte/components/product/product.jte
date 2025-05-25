@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param GroceryDTO grocery
@param ShoppingCartDTO shoppingCart
@param boolean isAuthenticated
@param boolean isAlternativeGrocerySuggestion = false
@param String groceryToChangeId = null
<div class="flex flex-col self-center items-center border border-black">
    <div style="width: 100%; padding-top: 100%; position: relative;">
        <img src="${grocery.imageUrl()}"
             alt="${grocery.name()}"
             style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
    </div>
    <h2 class="self-center clamp-text">
        ${grocery.name()}
    </h2>

    @for(GroceryVendorDTO vendor : grocery.sortVendorsByPrice())
        <div class="flex pb-1">
            <img src="${vendor.vendorDto().imageUrl()}" alt="${vendor.vendorDto().name()}" class="h-4">
            <p>€ ${vendor.price()} / ${grocery.unit()}</p>
        </div>
    @endfor
    @if(isAuthenticated)
        @if(isAlternativeGrocerySuggestion)
            <button
                    hx-put="/cart/add"
                    hx-vals='{"shoppingCartId": "${shoppingCart.id()}", "newGroceryId": "${grocery.id()}", "oldGroceryId": "${groceryToChangeId}", "quantity": "${grocery.quantity()}"}'
                    hx-target="#shopping-cart-container"
                    hx-swap="outerHTML"
                    class="font-medium text-indigo-600 hover:text-indigo-500 cursor-pointer"
            >
                Replace in Cart
            </button>
        @else
            <button
                    hx-post="/cart/add"
                    hx-vals='{"shoppingCartId": "${shoppingCart.id()}", "groceryId": "${grocery.id()}", "quantity": "${grocery.quantity()}"}'
                    hx-target="#shopping-cart-container"
                    hx-swap="outerHTML"
                    class="font-medium text-indigo-600 hover:text-indigo-500 cursor-pointer"
            >
                Add to Cart
            </button>
        @endif
    @endif
</div>

<style>
    .clamp-text {
        text-align: center;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
        height: 70px;
        line-height: 1.5em;
        word-wrap: break-word;
        margin: 0.5rem;
        transition: all 0.3s ease;
        position: relative;
    }
</style>