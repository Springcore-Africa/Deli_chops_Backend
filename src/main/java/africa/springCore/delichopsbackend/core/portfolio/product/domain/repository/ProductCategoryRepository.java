package africa.springCore.delichopsbackend.core.portfolio.product.domain.repository;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.model.Product;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByName(String name);
}
