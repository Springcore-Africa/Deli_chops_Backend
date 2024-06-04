package africa.springCore.delichopsbackend.core.portfolio.vendor.service;

import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorCreationException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorUpdateException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

public interface VendorService {
    VendorResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;
    VendorResponseDto createVendor(VendorCreationRequest VendorCreationRequest) throws DeliChopsException, VendorCreationException;

    VendorResponseDto findById(Long id) throws UserNotFoundException, MapperException;

    VendorListingDto retrieveAll(Pageable pageable) throws MapperException;

    VendorListingDto searchBy(String searchParam, String value, Pageable pageable);

    VendorResponseDto updateVendor(Long id, VendorUpdateRequest VendorUpdateRequest) throws VendorCreationException, UserNotFoundException, MapperException, VendorUpdateException;

    VendorResponseDto approveVendor(Long id, String actionName) throws UserNotFoundException, MapperException;
}
