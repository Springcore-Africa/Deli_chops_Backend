package africa.springCore.delichopsbackend.data.repositories;

import africa.springCore.delichopsbackend.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByBioData_EmailAddress(String email);

    Optional<Customer> findByBioData_PhoneNumber(String emailAddress);
}
