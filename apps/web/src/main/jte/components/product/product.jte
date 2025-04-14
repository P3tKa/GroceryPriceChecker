@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO

@param GroceryDTO grocery
@param ShoppingCartDTO shoppingCart
@param boolean isAuthenticated

<div class="flex flex-col self-center items-center border border-black">
    <div style="width: 100%; padding-top: 100%; position: relative;">
        <img src="${grocery.imageUrl()}"
             alt="${grocery.name()}"
             style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
    </div>
    <h2 class="self-center" style="height: 70px; text-align: center">${grocery.name()}</h2>

    @for(GroceryVendorDTO vendor : grocery.sortVendorsByPrice())
        <div class="flex">
            <img src="${vendor.vendorDto().imageUrl()}" alt="${vendor.vendorDto().name()}" class="h-4">
            <p>€ ${vendor.price()} / ${grocery.unit()}</p>
        </div>
    @endfor
    @if(isAuthenticated)
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
</div>
