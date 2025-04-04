<script>
    function togglePanel() {
        const shoppingCartContainer = document.getElementById('shopping-cart-container');
        shoppingCartContainer.style.display = shoppingCartContainer.style.display === 'block' ? 'none' : 'block';

        document.getElementById('backdrop').classList.toggle('show-cart-backdrop');
        document.getElementById('slideOverPanel').classList.toggle('show-cart');
    }
</script>

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
           hx-indicator="#loader-icon"
    >

    <div class="size-12 m-2">
        <button
                id="shopping-cart"
                class="relative inline-block cursor-pointer"
                hx-trigger="click"
                hx-swap="none"
                hx-vals='{"toggle": "true"}'
                onclick="togglePanel()"
        >
            <img src="/svg/cart.svg" alt="Shopping cart" class="z-10 absolute">
            <img src="/svg/groceries.svg" id="groceries" alt="Groceries inside shopping cart">
        </button>
    </div>
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
