@param dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryDTO grocery

<div class="flex flex-col self-center items-center border border-black">
    <img src="${grocery.imageUrl()}"
         alt="${grocery.name()}"
         style="width: 80%; height: 80%; object-fit: cover">
    <h2 class="self-center" style="height: 50px; text-align: center">${grocery.name()}</h2>

    @for(dev.petkevicius.groceryPriceChecker.domain.groceries.dto.GroceryVendorDTO vendor : grocery.sortVendorsByPrice())
        <div class="flex">
            <img src="${vendor.vendorDto().imageUrl()}" alt="${vendor.vendorDto().name()}" class="h-4">
<%--            <p>${vendor.vendorDto().name()}</p>--%>
            <p>€ ${vendor.price()} / ${grocery.unit()}</p>
        </div>
    @endfor
</div>
