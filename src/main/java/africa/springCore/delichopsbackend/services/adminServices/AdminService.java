package africa.springCore.delichopsbackend.services.adminServices;

import africa.springCore.delichopsbackend.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;

public interface AdminService {
    AdminResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;
}
