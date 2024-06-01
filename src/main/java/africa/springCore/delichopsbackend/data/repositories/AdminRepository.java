package africa.springCore.delichopsbackend.data.repositories;

import africa.springCore.delichopsbackend.data.models.Admin;
import africa.springCore.delichopsbackend.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByBioData_EmailAddress(String emailAddress);
}
