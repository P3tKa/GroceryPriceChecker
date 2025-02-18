@param lt.p3tka.groceryPriceChecker.controller.DemoController.Message model

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styles.css">
    <script src="https://unpkg.com/htmx.org/dist/htmx.js"></script>
    <title>Document</title>
</head>
<body>

<div class="flex flex-col items-center justify-center h-screen">
<p>Hello ${model.message()}!</p>
<p>Visitor count ${model.visitorCount()}</p>
</div>

</body>
</html>
