package africa.springCore.delichopsbackend.core.base.domain.repository;

import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.core.base.domain.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndUserType(Long id, Role userType);
    Page<Address> findByUserIdAndUserType(Long userId, Role userType, Pageable pageable);
}
