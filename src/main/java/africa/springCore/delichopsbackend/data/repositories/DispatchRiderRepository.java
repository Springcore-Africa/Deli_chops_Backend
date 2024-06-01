package africa.springCore.delichopsbackend.data.repositories;

import africa.springCore.delichopsbackend.data.models.Admin;
import africa.springCore.delichopsbackend.data.models.DispatchRider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DispatchRiderRepository extends JpaRepository<DispatchRider, Long> {

    Optional<DispatchRider> findByBioData_EmailAddress(String emailAddress);
}
