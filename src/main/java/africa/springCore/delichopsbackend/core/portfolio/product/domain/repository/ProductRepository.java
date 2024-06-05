package africa.springCore.delichopsbackend.core.portfolio.product.domain.repository;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByVendorId(Long vendorId, Pageable pageable);
    Page<Product> findByName(String productName, Pageable pageable);
}
