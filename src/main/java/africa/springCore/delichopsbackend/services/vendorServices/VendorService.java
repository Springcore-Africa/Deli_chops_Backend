package africa.springCore.delichopsbackend.services.vendorServices;

import africa.springCore.delichopsbackend.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;

public interface VendorService {
    VendorResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;
}
