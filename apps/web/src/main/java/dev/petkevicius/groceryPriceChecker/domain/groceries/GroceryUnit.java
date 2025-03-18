package dev.petkevicius.groceryPriceChecker.domain.groceries;

public enum GroceryUnit {
    KG,
    L,
    VNT;

    public static GroceryUnit fromString(String unit) {
        for (GroceryUnit groceryUnit : GroceryUnit.values()) {
            if (groceryUnit.name().equalsIgnoreCase(unit)) {
                return groceryUnit;
            }
        }

        throw new IllegalArgumentException("No enum constant for unit: " + unit);
    }
}
