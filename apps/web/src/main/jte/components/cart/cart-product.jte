@param dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO grocery

<li class="flex py-6">
    <div class="size-24 shrink-0 overflow-hidden rounded-md border border-gray-200">
        <img src="${grocery.imageUrl()}" alt="${grocery.name()}" class="size-full object-cover">
    </div>

    <div class="ml-4 flex flex-1 flex-col">
        <div>
            <div class="flex justify-between text-base font-medium text-gray-900">
                <h3>
                    <a href="#">${grocery.name()}</a>
                </h3>
                <p class="ml-4">€${grocery.groceryVendorDTOS().getFirst().price()}</p>
            </div>
            <p class="mt-1 text-sm text-gray-500">${grocery.category()}</p>
        </div>
        <div class="flex flex-1 items-end justify-between text-sm">
            <p class="text-gray-500">${grocery.quantity()} ${grocery.unit()}</p>

            <div class="flex">
                <button type="button" class="font-medium text-indigo-600 hover:text-indigo-500">Remove</button>
            </div>
        </div>
    </div>
</li>