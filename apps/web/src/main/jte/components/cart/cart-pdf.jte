@import java.util.Map
@import java.util.List
@import java.math.BigDecimal
@import java.util.stream.Collectors
@import dev.petkevicius.groceryPriceChecker.domain.groceries.VendorName
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO
@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartGroceryDTO

@param ShoppingCartDTO shoppingCart

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="description" content="Save money by comparing grocery prices across different vendors with. Find the best deals and lowest prices for your groceries." />
    <link rel="icon" type="image/png" href="/seo/favicon-96x96.png" sizes="96x96" />
    <link rel="icon" type="image/svg+xml" href="/seo/favicon.svg" />
    <link rel="shortcut icon" href="/seo/favicon.ico" />
    <link rel="apple-touch-icon" sizes="180x180" href="/seo/apple-touch-icon.png" />
    <link rel="manifest" href="/seo/site.webmanifest" />
    <link rel="stylesheet" href="/css/styles.css">
    <script src="https://unpkg.com/htmx.org/dist/htmx.js"></script>
    <title>GroceryPriceChecker</title>
</head>
<body>
<div class="p-6 max-w-3xl mx-auto bg-white rounded-lg border border-gray-200">
    @if(shoppingCart == null)
        <p class="text-center text-gray-400 text-lg font-medium">Your shopping cart is empty.</p>
    @else
        <h1 class="text-2xl font-semibold text-gray-800 mb-4 text-center">Shopping Cart</h1>
        <ul class="space-y-4">
            @for(Map.Entry<VendorName, List<ShoppingCartGroceryDTO>> entry : shoppingCart.shoppingCartGroceries().stream()
                .collect(Collectors.groupingBy(
                    grocery -> grocery.grocery().groceryVendorDTOS().getFirst().vendorDto().name()
                )).entrySet())
                <li class="p-4 bg-gray-50 rounded-lg border border-gray-200">
                    <div class="flex items-center mb-3">
                        <img src="${entry.getValue().get(0).grocery().groceryVendorDTOS().getFirst().vendorDto().imageUrl()}"
                             alt="${entry.getKey()}"
                             class="w-10 h-10 object-contain rounded-full bg-white border border-gray-300">
                        <h2 class="ml-3 text-lg font-medium text-gray-700">${entry.getKey()}</h2>
                    </div>
                    <ul class="space-y-2">
                        @for(ShoppingCartGroceryDTO grocery : entry.getValue())
                            <li class="flex justify-between items-center p-3 bg-gray-100 rounded-md hover:bg-gray-200 transition">
                                <div class="flex items-center">
                                    <span class="text-gray-700">${grocery.grocery().name()}</span>
                                    <span class="ml-2 px-2 py-1 text-xs font-semibold text-gray-800 bg-gray-200 rounded-full">
                    Qty: ${grocery.quantity()} ${grocery.grocery().unit()}
                </span>
                                </div>
                                <span class="text-gray-600 font-medium">€${grocery.grocery().groceryVendorDTOS().getFirst().price()}</span>
                            </li>
                        @endfor
                    </ul>
                </li>
            @endfor
        </ul>
        <div class="mt-6 p-4 bg-gray-50 rounded-lg border border-gray-200 text-lg font-medium text-gray-800 flex justify-between">
            <p>Total</p>
            <p>€
                ${shoppingCart.shoppingCartGroceries().stream()
                    .map(grocery -> grocery.grocery().groceryVendorDTOS().getFirst().price())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                }
            </p>
        </div>
    @endif
</div>
</body>
</html>

