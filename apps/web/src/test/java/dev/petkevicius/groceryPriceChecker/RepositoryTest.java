package dev.petkevicius.groceryPriceChecker;

import jakarta.transaction.Transactional;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@Transactional
@Rollback
public class RepositoryTest {
}
