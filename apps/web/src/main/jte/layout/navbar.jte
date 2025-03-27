<div class="flex justify-between align-center h-22">
    <a href="/" class="m-2 p-1 border-1">
        <p class="text-md leading-5">
            Grocery<br>
            Price<br>
            Checker
        </p>
    </a>
    <input type="search"
           name="query"
           placeholder="Search"
           class="border border-gray-300 rounded-lg p-2 mt-4 h-10"
           hx-get="/search"
           hx-trigger="input changed delay:500ms, keyup[key=='Enter']:not([value=''])"
           hx-target="#grocery-list"
           hx-swap="outerHTML"
           hx-indicator="#htmx-loader"
    >

    <div class="size-12 m-2">
        <a href="/cart" id="shopping-cart" class="relative inline-block">
            <img src="/svg/cart.svg" alt="Shopping cart" class="z-10 absolute">
            <img src="/svg/groceries.svg" id="groceries" alt="Groceries inside shopping cart">
        </a>
    </div>
</div>

<div id="htmx-loader">
    testing
</div>

<style>
    #groceries {
        transition: transform 0.2s;
    }

    #shopping-cart:hover {
        transform: scale(1.05);
        #groceries {
            transform: scale(1.02) rotate(-2deg);
        }
    }
</style>
