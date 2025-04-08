@param boolean isAuthenticated = false

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
           class="relative left-10 border border-gray-300 rounded-lg p-2 mt-4 h-10"
           hx-get="/search"
           hx-trigger="input changed delay:500ms, keyup[key=='Enter']:not([value=''])"
           hx-target="#grocery-list"
           hx-swap="outerHTML"
           hx-indicator="#loader-icon"
    >
    <div class="flex">
        <div class="relative">
            @if(isAuthenticated)
                <button
                        popovertarget="login-options"
                        style="anchor-name: login-options;"
                        class="cursor-pointer relative size-12 m-4 hover:scale-110 transition-transform duration-200 ease-in-out"
                >
                    <img src="/svg/avatar.svg" alt="Avatar">
                </button>
                <div
                        id="login-options"
                        class="login-options"
                        style="position-anchor: login-options; position: absolute; top: anchor(bottom); left: calc(anchor(left) - 1.75rem);"
                        popover="auto"
                >
                    <a href="/logout" class="block px-4 py-2 text-gray-800 hover:bg-gray-200">Logout</a>
                </div>
            @else
                <button
                        popovertarget="login-options"
                        style="anchor-name: login-options;"
                        class="w-[30px] h-[30px] flex flex-row justify-center m-5 mr-9 align-center cursor-pointer relative m-4 hover:scale-110 transition-transform duration-200 ease-in-out"
                >
                    <span class="whitespace-nowrap mt-[2px]">Sign in</span>
                    <img src="/svg/signIn.svg" alt="Sign in">
                </button>
                @template.components.auth.signIn()
            @endif
        </div>
        <div class="size-12 m-2">
<%--            <button--%>
<%--                    id="shopping-cart"--%>
<%--                    class="relative inline-block cursor-pointer"--%>
<%--                    hx-trigger="click"--%>
<%--                    hx-swap="none"--%>
<%--                    hx-vals='{"toggle": "true"}'--%>
<%--                    onclick="togglePanel()"--%>
<%--            >--%>
            <button
                    id="shopping-cart"
                    class="relative inline-block cursor-pointer"
                    hx-get="/cart"
                    hx-target="#shopping-cart-container"
                    hx-swap="outerHTML">
                <img src="/svg/cart.svg" alt="Shopping cart" class="z-10 absolute">
                <img src="/svg/groceries.svg" id="groceries" alt="Groceries inside shopping cart">
            </button>
        </div>
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
