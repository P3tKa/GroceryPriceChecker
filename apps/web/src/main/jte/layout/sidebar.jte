@param dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category[] categories

<nav class="absolute z-10">
    <ul>
        @for(dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category category : categories)
        <li class="p-1 pr-4 relative">
            <button class="w-full text-left" popovertarget="${category.name()}" style="anchor-name: ${category.name()};">
                ${category.getDisplayName()}
                <span class="arrow-button"></span>
            </button>
            <ul popover="auto" id="${category.name()}" style="position-anchor: ${category.name()}; position: absolute; top: anchor(top); left: calc(anchor(right) + 1.5rem);">
                <li class="p-1 pr-4"><a href="/${category.name()}" class="underline">Žiurėti visus produktus</a></li>
                @for(dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category.SubCategory subCategory : category.getSubCategories())
                <li class="p-1 pr-4 relative">
                    <button class="w-full text-left" popovertarget="${subCategory.name()}" style="anchor-name: ${subCategory.name()};">
                        ${subCategory.getDisplayName()}
                        <span class="arrow-button"></span>
                    </button>
                    <ul popover="auto" id="${subCategory.name()}" style="position-anchor: ${subCategory.name()}; position: absolute; top: anchor(top); left: calc(anchor(right) + 1.5rem);">
                        <li class="p-1 pr-4"><a href="/${subCategory.name()}" class="underline">Žiurėti visus produktus</a></li>
                        @for(dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category.SubSubCategory subSubCategory : subCategory.getSubSubCategories())
                        <li class="p-1 pr-4"><a href="/${subSubCategory.name()}">${subSubCategory.getDisplayName()}</a></li>
                        @endfor
                    </ul>
                </li>
                @endfor
            </ul>
        </li>
        @endfor
    </ul>
</nav>