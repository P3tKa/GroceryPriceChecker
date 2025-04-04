@param String shoppingCartId
@param dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartGroceryDTO shoppingCartGrocery

<li class="flex py-6">
    <div class="size-24 shrink-0 overflow-hidden rounded-md border border-gray-200">
        <img src="${shoppingCartGrocery.grocery().imageUrl()}" alt="${shoppingCartGrocery.grocery().name()}" class="size-full object-cover">
    </div>

    <div class="ml-4 flex flex-1 flex-col">
        <div>
            <div class="flex justify-between text-base font-medium text-gray-900">
                <h3>
                    <a href="#">${shoppingCartGrocery.grocery().name()}</a>
                </h3>
                <p class="ml-4">€${shoppingCartGrocery.grocery().groceryVendorDTOS().getFirst().price()}</p>
            </div>
            <p class="mt-1 text-sm text-gray-500">${shoppingCartGrocery.grocery().category()}</p>
        </div>
        <div class="flex flex-1 items-end justify-between text-sm">
            <p class="text-gray-500">${shoppingCartGrocery.grocery().quantity()} ${shoppingCartGrocery.grocery().unit()}</p>
            <div class="flex">
                <button
                        hx-delete="/cart/${shoppingCartId}/remove"
                        hx-vals='{"groceryId": "${shoppingCartGrocery.grocery().id()}"}'
                        hx-target="#shopping-cart-container"
                        hx-swap="outerHTML"
                        class="font-medium text-indigo-600 hover:text-indigo-500">
                    Remove
                </button>
            </div>
        </div>
    </div>
</li>