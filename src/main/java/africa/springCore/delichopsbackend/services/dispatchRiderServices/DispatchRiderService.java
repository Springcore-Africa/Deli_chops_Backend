package africa.springCore.delichopsbackend.services.dispatchRiderServices;

import africa.springCore.delichopsbackend.dtos.responses.DispatchRiderResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;

public interface DispatchRiderService {
    DispatchRiderResponseDto findByEmail(String emailAddress) throws UserNotFoundException, MapperException;
}
