package africa.springCore.delichopsbackend.core.portfolio.dispatchRider.service;

import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.domain.dtos.responses.DispatchRiderResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;

public interface DispatchRiderService {
    DispatchRiderResponseDto findByEmail(String emailAddress) throws UserNotFoundException, MapperException;
}
