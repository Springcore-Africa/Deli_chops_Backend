package africa.springCore.delichopsbackend.data.repositories;

import africa.springCore.delichopsbackend.data.models.Admin;
import africa.springCore.delichopsbackend.data.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByBioData_EmailAddress(String emailAddress);
}
