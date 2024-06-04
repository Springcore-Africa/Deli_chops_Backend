package africa.springCore.delichopsbackend.core.base.service;

import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.core.base.domain.dtos.request.AddressCreationRequest;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.AddressListingDto;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.AddressResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.AddressNotFoundException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    AddressResponseDto createUserAddress(AddressCreationRequest addressCreationRequest, Role userType, Long userId) throws MapperException;

    AddressResponseDto findById(Long id, Role userType) throws MapperException, AddressNotFoundException;

    AddressListingDto findByUserId(Long userId, Role customer, Pageable pageable);

    AddressResponseDto updateUserAddress(AddressCreationRequest addressCreationRequest, Long customerId, Role userType, Long addressId) throws AddressNotFoundException, MapperException;
}
