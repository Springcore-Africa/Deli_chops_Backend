package africa.springCore.delichopsbackend.data.repositories;

import africa.springCore.delichopsbackend.data.models.Admin;
import africa.springCore.delichopsbackend.data.models.BioData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioDataRepository extends JpaRepository<BioData, Long> {

    Optional<BioData> findByEmailAddress(String email);
}
