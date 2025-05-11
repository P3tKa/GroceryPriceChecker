@import java.util.List
@import dev.petkevicius.groceryPriceChecker.domain.groceries.dto.CheapestVendorDTO

@param List<CheapestVendorDTO> cheapestVendorsByCategory

<div class="flex absolute top-0">
    <button
        popovertarget="chart-popover"
        class="cursor-pointer relative size-12 m-4 hover:scale-105 transition-transform duration-200 ease-in-out"
    >
        <img src="/svg/bar-chart.svg" alt="Bar chart" />
    </button>
    <div
        id="chart-popover"
        popover="auto"
        style="position-anchor: login-options; position: absolute; top: calc(anchor(top) - 3.5rem); left: calc(anchor(left) + 3rem);"
        class="border-2 border-gray-300 rounded-lg shadow-lg p-4 bg-white scale-80"
    >
        <div class="flex flex-col">
            @for(int i = 0; i < cheapestVendorsByCategory.size(); i++)
                <div class="flex flex-row items-center">
                    <span class="mr-2 text-lg scale-150
                        @if(i == 0) text-yellow-500
                        @elseif(i == 1) text-gray-400
                        @elseif(i == 2) text-amber-700
                        @else text-gray-500
                        @endif">
                        @if(i == 0) 🥇
                        @elseif(i == 1) 🥈
                        @elseif(i == 2) 🥉
                        @else ⭐
                        @endif
                    </span>
                    <img src="${cheapestVendorsByCategory.get(i).imageUrl()}" alt="Vendor Image" class="w-16 h-10 rounded-full object-contain" />                </div>
            @endfor
        </div>
    </div>
</div>