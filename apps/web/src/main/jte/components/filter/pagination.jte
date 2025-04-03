@param int currentPage
@param int totalPages

<nav class="flex flex-row items-center justify-center p-1 rounded bg-white space-x-2">
    <div class="w-5">
        @if(currentPage > 0)
            <a class="p-1 text-black bg-white"
               href="javascript:void(0);"
               onclick="navigateToPage(${currentPage - 1})"
            >
                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" />
                </svg>
            </a>
        @endif
    </div>
    <p>Page ${currentPage + 1} of ${totalPages}</p>
    <div class="w-5">
        @if (currentPage + 1 < totalPages)
            <a class="p-1 text-black bg-white"
               href="javascript:void(0);"
               onclick="navigateToPage(${currentPage + 1})"
            >
                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                          d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z" />
                </svg>
            </a>
        @endif
    </div>
</nav>

<script>
    function navigateToPage(page) {
        const urlParams = new URLSearchParams(window.location.search);
        urlParams.set('page', page);
        window.location.search = urlParams.toString();
    }
</script>