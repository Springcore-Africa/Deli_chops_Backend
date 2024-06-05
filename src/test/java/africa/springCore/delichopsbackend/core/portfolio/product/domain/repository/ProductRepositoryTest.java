package africa.springCore.delichopsbackend.core.portfolio.product.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void test() {
        // Create the native SQL query
        String dropTableQuery = "DROP TABLE IF EXISTS product";

        // Execute the native SQL query
        entityManager.createNativeQuery(dropTableQuery).executeUpdate();
    }
}