package dev.petkevicius.groceryPriceChecker.repository.groceries;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;

import dev.petkevicius.groceryPriceChecker.RepositoryTest;
import dev.petkevicius.groceryPriceChecker.domain.groceries.Grocery;
import dev.petkevicius.groceryPriceChecker.domain.groceries.GroceryUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;

public class GroceryRepositoryTest extends RepositoryTest {

    @Autowired
    PostgreSQLContainer<?> postgres;

    @Autowired
    GroceryRepository groceryRepository;

    @Test
    void savesGrocery() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();

        assertThat(groceryRepository.count()).isZero();

        Grocery grocery = Grocery.builder()
            .id("1")
            .name("Banana")
            .quantity(BigDecimal.ONE)
            .unit(GroceryUnit.KG)
            .build();

        groceryRepository.save(grocery);

        assertThat(groceryRepository.count()).isOne();
    }

}