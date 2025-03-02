@import gg.jte.Content

@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/styles.css">
    <script src="https://unpkg.com/htmx.org/dist/htmx.js"></script>
    <title>GroceryPriceChecker</title>
</head>
<body>
<nav>
    @template.layout.navbar()
</nav>
<main>
    ${content}
</main>
<footer>
    @template.layout.footer()
</footer>
</body>
</html>