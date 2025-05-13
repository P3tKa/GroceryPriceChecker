@import java.math.BigDecimal
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartGroceryDTO

@param ShoppingCartDTO shoppingCart
@param String error = ""
@param boolean opened = false

<div id="shopping-cart-container" style="display: ${opened ? "block" : "none"}" class="absolute z-10 mt-10 pt-10">
    <div id="backdrop"
         class="fixed inset-0 bg-gray-500/75 transition-opacity shopping-cart-backdrop ${opened ? "show-cart-backdrop" : ""}"
         aria-hidden="true">

    </div>

    <div class="fixed inset-0 overflow-hidden">
        <div class="absolute insaet-0 overflow-hidden">
            <div class="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
                <div id="alternative-list" class="pointer-events-auto bg-white border z-100 border-gray-200 rounded-lg shadow-lg"></div>
                <div id="slideOverPanel"
                     class="pointer-events-auto w-screen max-w-md shopping-cart ${opened ? "show-cart" : ""}"
                >
                    <div class="flex h-full flex-col overflow-y-scroll bg-white shadow-xl">
                        <div class="flex-1 overflow-y-auto px-4 py-6 sm:px-6">
                            <div class="flex items-start justify-between">
                                <h2 class="text-lg font-medium text-gray-900" id="slide-over-title">Shopping cart</h2>
                                <div class="ml-3 flex h-7 items-center">
                                    <button type="button"
                                            class="relative -m-2 p-2 text-gray-400 hover:text-gray-500 cursor-pointer"
                                            hx-trigger="click"
                                            hx-swap="none"
                                            hx-vals='{"toggle": "true"}'
                                            onclick="togglePanel()"
                                    >
                                        <span class="absolute -inset-0.5"></span>
                                        <span class="sr-only">Close panel</span>
                                        <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
                                        </svg>
                                    </button>
                                </div>
                            </div>

                            @if(!error.isEmpty())
                                <p>${"Sign in to use shopping cart"}</p>
                            @else
                            <div class="mt-8">
                                <div class="flow-root">
                                    <ul role="list" class="-my-6 divide-y divide-gray-200">
                                        @if(shoppingCart.shoppingCartGroceries().isEmpty())
                                            <div class="flex flex-col items-center justify-center pt-40 text-center text-gray-500">
                                                <svg class="w-16 h-16 mb-4 text-gray-300" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h18M9 3v18m6-18v18M4 21h16M5 7h14M5 11h14M5 15h14" />
                                                </svg>
                                                <p class="text-lg font-medium">Your cart is empty</p>
                                            </div>
                                        @endif
                                        @for(ShoppingCartGroceryDTO shoppingCartGrocery : shoppingCart.shoppingCartGroceries())
                                            @template.components.cart.cart-product(shoppingCartId = shoppingCart.id(), shoppingCartGrocery = shoppingCartGrocery, mostPopularCheapestVendor = shoppingCart.cheapestVendor())
                                        @endfor
                                    </ul>
                                </div>
                            </div>
                            @endif
                        </div>

                        @if(error.isEmpty())
                        <div class="border-t border-gray-200 px-4 py-6 sm:px-6">
                            <div class="flex justify-between text-base font-medium text-gray-900">
                                <p>Subtotal</p>
                                <p>€
                                    ${shoppingCart.shoppingCartGroceries().stream()
                                        .map(grocery -> grocery.grocery().groceryVendorDTOS().getFirst().price())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    }
                                </p>
                            </div>
                            <p class="mt-0.5 text-sm text-gray-500">Cheapest price across all vendors</p>
                            <div class="mt-6">
                                <a href="#" class="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-xs hover:bg-indigo-700">Checkout</a>
                            </div>
                            <div class="mt-6">
                                <button onclick="openAndPrintPdf()"
                                        class="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-xs hover:bg-indigo-700">
                                    Save as PDF
                                </button>
                            </div>
                        </div>
                        @endif
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function openAndPrintPdf() {
        const pdfWindow = window.open('/cart/pdf', '_blank');
        pdfWindow.onload = () => {
            pdfWindow.print();
        };
    }
</script>