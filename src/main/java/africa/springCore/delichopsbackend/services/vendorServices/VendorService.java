package africa.springCore.delichopsbackend.services.vendorServices;

import africa.springCore.delichopsbackend.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.exception.*;
import org.springframework.data.domain.Pageable;

public interface VendorService {
    VendorResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;
    VendorResponseDto createVendor(VendorCreationRequest VendorCreationRequest) throws DeliChopsException, VendorCreationException;

    VendorResponseDto findById(Long id) throws UserNotFoundException, MapperException;

    VendorListingDto retrieveAll(Pageable pageable) throws MapperException;

    VendorListingDto searchBy(String searchParam, String value, Pageable pageable);

    VendorResponseDto updateVendor(Long id, VendorUpdateRequest VendorUpdateRequest) throws VendorCreationException, UserNotFoundException, MapperException, VendorUpdateException;
}
