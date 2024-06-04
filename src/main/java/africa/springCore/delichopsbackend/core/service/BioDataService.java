package africa.springCore.delichopsbackend.core.service;

import africa.springCore.delichopsbackend.core.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;

public interface BioDataService {
    BioDataResponseDto findByEmail(String email) throws UserNotFoundException, MapperException;
}
