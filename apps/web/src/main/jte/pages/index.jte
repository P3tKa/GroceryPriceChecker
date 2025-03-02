@param dev.petkevicius.groceryPriceChecker.controller.DemoController.Message model

@template.layout.main(content = @`
    <div class="text-center content-center h-screen">
        <p>Hello ${model.message()}!</p>
        <p>Visitor count ${model.visitorCount()}</p>
    </div>
`)
