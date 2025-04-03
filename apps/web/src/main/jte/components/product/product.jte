@param dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO grocery

<div class="flex flex-col self-center items-center border border-black">
    <div style="width: 100%; padding-top: 100%; position: relative;">
        <img src="${grocery.imageUrl()}"
             alt="${grocery.name()}"
             style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;">
    </div>
    <h2 class="self-center" style="height: 70px; text-align: center">${grocery.name()}</h2>

    @for(dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO vendor : grocery.sortVendorsByPrice())
        <div class="flex">
            <img src="${vendor.vendorDto().imageUrl()}" alt="${vendor.vendorDto().name()}" class="h-4">
            <p>€ ${vendor.price()} / ${grocery.unit()}</p>
        </div>
    @endfor
</div>
