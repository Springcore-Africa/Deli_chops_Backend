package africa.springCore.delichopsbackend.core.portfolio.vendor.domain.repository;

import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.Vendor;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.VendorRegistrationDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRegistrationDocumentRepository extends JpaRepository<VendorRegistrationDocument, Long> {

    Page<VendorRegistrationDocument> findByVendorId(Long vendorId, Pageable pageable);
}
