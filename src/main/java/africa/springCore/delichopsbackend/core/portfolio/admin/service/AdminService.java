package africa.springCore.delichopsbackend.core.portfolio.admin.service;

import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;

public interface AdminService {
    AdminResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;
}
