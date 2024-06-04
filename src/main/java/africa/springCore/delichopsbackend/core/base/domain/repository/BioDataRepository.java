package africa.springCore.delichopsbackend.core.base.domain.repository;

import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioDataRepository extends JpaRepository<BioData, Long> {

    Optional<BioData> findByEmailAddress(String email);
}
