@import dev.petkevicius.groceryPriceChecker.domain.shoppingCart.dto.ShoppingCartDTO
@import gg.jte.Content
@import dev.petkevicius.groceryPriceChecker.domain.groceries.common.Category

@param Content content
@param Category[] categories
@param ShoppingCartDTO shoppingCart

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="description" content="Save money by comparing grocery prices across different vendors with. Find the best deals and lowest prices for your groceries." />
    <link rel="icon" type="image/png" href="/seo/favicon-96x96.png" sizes="96x96" />
    <link rel="icon" type="image/svg+xml" href="/seo/favicon.svg" />
    <link rel="shortcut icon" href="/seo/favicon.ico" />
    <link rel="apple-touch-icon" sizes="180x180" href="/seo/apple-touch-icon.png" />
    <link rel="manifest" href="/seo/site.webmanifest" />
    <link rel="stylesheet" href="/css/styles.css">
    <script src="https://unpkg.com/htmx.org/dist/htmx.js"></script>
    <title>GroceryPriceChecker</title>
</head>
<body>
<nav>
    @template.layout.navbar()
</nav>
<aside>
    @template.layout.sidebar(categories = categories)
</aside>
<aside>
    @template.layout.cart(opened = false, shoppingCart = shoppingCart)
</aside>
<main>
    ${content}
</main>
<footer>
    @template.layout.footer()
</footer>
</body>
</html>