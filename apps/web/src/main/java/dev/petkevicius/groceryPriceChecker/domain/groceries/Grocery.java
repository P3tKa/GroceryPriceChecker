package dev.petkevicius.groceryPriceChecker.domain.groceries;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groceries")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grocery {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    Float quantity;

    @Column(name = "unit")
    GroceryUnit unit;
}

