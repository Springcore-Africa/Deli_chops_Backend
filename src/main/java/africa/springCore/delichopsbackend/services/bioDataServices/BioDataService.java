package africa.springCore.delichopsbackend.services.bioDataServices;

import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;

public interface BioDataService {
    BioDataResponseDto findByEmail(String email) throws UserNotFoundException, MapperException;
}
