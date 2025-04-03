@param java.util.Optional<dev.petkevicius.groceryPriceChecker.controller.model.SortValue> sortValue

<select
    class="ml-auto"
    name="sortBy"
    aria-label="Rikiuoti pagal"
    onchange="this.form.submit()"
>
    <button>
    <span aria-hidden="true">
      <selectedcontent></selectedcontent>
    </span>
        <span class="arrow">
      <img src="https://www.svgrepo.com/download/80156/down-arrow.svg" alt="">
    </span>
    </button>
    <option value="" disabled selected hidden>Paieška</option>
    <option
        value="PRICE_ASC"
        selected="${sortValue.map(sort -> dev.petkevicius.groceryPriceChecker.controller.model.SortValue.PRICE_ASC.equals(sort)).orElse(false)}"
    >
        Kaina: nuo žemiausios
    </option>
    <option
        value="PRICE_DESC"
        selected="${sortValue.map(sort -> dev.petkevicius.groceryPriceChecker.controller.model.SortValue.PRICE_DESC.equals(sort)).orElse(false)}"
    >
        Kaina: nuo aukščiausios
    </option>
</select>