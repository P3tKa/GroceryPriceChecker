@param boolean showPopoverWrap = false

@if(showPopoverWrap)
    <div
    id="login-options"
    popover="auto"
    style="
            position-anchor: login-options;
            position: absolute;
            top: calc(anchor(bottom) - 2rem);
            left: calc(anchor(right) - 25rem);
            transform: scale(0.8);
        "
    class="relative mx-auto w-full max-w-md bg-white px-6 pt-10 pb-8 shadow-xl ring-1 ring-gray-900/5 sm:rounded-xl sm:px-10"
    >@endif
<div class="w-full">
    <div class="text-center">
        <h1 class="text-3xl font-semibold text-gray-900">Sign in</h1>
        <p class="mt-2 text-gray-500">Sign in below to access your account</p>
    </div>
    <div id="error-container" class="mt-4 text-red-500 text-center hidden">
        <p id="error-message"></p>
    </div>
    <div class="mt-5">
        <form hx-post="/login" hx-target="#error-container" hx-swap="none" hx-on="htmx:responseError">
            <div class="relative mt-6">
                <input type="email" name="username" id="username" placeholder="Email Address"
                       class="peer mt-1 w-full border-b-2 border-gray-300 px-0 py-1 placeholder:text-transparent focus:border-gray-500 focus:outline-none"
                       autocomplete="NA"/>
                <label for="email"
                       class="pointer-events-none absolute top-0 left-0 origin-left -translate-y-1/2 transform text-sm text-gray-800 opacity-75 transition-all duration-100 ease-in-out peer-placeholder-shown:top-1/2 peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-500 peer-focus:top-0 peer-focus:pl-0 peer-focus:text-sm peer-focus:text-gray-800">Email
                    Address</label>
            </div>
            <div class="relative mt-6">
                <input type="password" name="password" id="password" placeholder="Password"
                       class="peer peer mt-1 w-full border-b-2 border-gray-300 px-0 py-1 placeholder:text-transparent focus:border-gray-500 focus:outline-none"/>
                <label for="password"
                       class="pointer-events-none absolute top-0 left-0 origin-left -translate-y-1/2 transform text-sm text-gray-800 opacity-75 transition-all duration-100 ease-in-out peer-placeholder-shown:top-1/2 peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-500 peer-focus:top-0 peer-focus:pl-0 peer-focus:text-sm peer-focus:text-gray-800">Password</label>
            </div>
            <div class="my-6">
                <button type="submit"
                        class="w-full rounded-md bg-black px-3 py-4 text-white focus:bg-gray-600 focus:outline-none">
                    Sign in
                </button>
            </div>
            <p class="text-center text-sm text-gray-500">Don't have an account yet?
                <button
                        hx-get="/auth/signUp"
                        hx-target="#login-options"
                        hx-swap="innerHTML"
                        class="cursor-pointer font-semibold text-gray-600 hover:underline focus:text-gray-800 focus:outline-none">
                    Sign up
                </button>
            </p>
        </form>
        <div class="flex w-full items-center gap-2 py-6 text-sm text-slate-600">
            <div class="h-px w-full bg-slate-200"></div>
            OR
            <div class="h-px w-full bg-slate-200"></div>
        </div>
        <a href="/oauth2/authorization/google"
           class="inline-flex h-10 w-full items-center justify-center gap-2 rounded border border-slate-300 bg-white p-2 text-sm font-medium text-black outline-none focus:ring-2 focus:ring-[#333] focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-60">
            <img
                    src="https://www.svgrepo.com/show/475656/google-color.svg" alt="Google"
                    class="h-[18px] w-[18px] ">
            Continue with Google
        </a>
    </div>
</div>
<script>
    document.body.addEventListener('htmx:responseError', function (event) {
        if (event.detail.xhr.status === 401) {
            const errorContainer = document.getElementById('error-container');
            const errorMessage = document.getElementById('error-message');
            errorMessage.textContent = "Invalid credentials. Please try again.";
            errorContainer.classList.remove('hidden');
        }
    });

    document.body.addEventListener('htmx:afterRequest', function (event) {
        if (event.detail.xhr.status === 200 && event.target.tagName === 'FORM') {
            window.location.reload();
        }
    });
</script>
@if(showPopoverWrap)
    </div>
@endif