@import java.util.Optional
@import org.springframework.data.domain.Pageable

@param org.springframework.data.domain.Pageable pageable

<select
    class="ml-auto"
    name="sort"
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
        value="price,asc"
        selected="${Optional.ofNullable(pageable)
            .map(Pageable::getSort)
            .flatMap(sort -> Optional.ofNullable(sort.getOrderFor("price")))
            .map(order -> order.getDirection().isAscending())
            .orElse(false)}"
    >
        Kaina: nuo žemiausios
    </option>
    <option
        value="price,desc"
        selected="${Optional.ofNullable(pageable)
            .map(Pageable::getSort)
            .flatMap(sort -> Optional.ofNullable(sort.getOrderFor("price")))
            .map(order -> order.getDirection().isDescending())
            .orElse(false)}"
    >
        Kaina: nuo aukščiausios
    </option>
</select>