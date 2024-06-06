package africa.springCore.delichopsbackend.core.portfolio.admin.service;

import africa.springCore.delichopsbackend.common.data.ApiResponse;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.requests.AdminInvitationRequest;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.requests.AdminUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminListingDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.exception.AdminNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.admin.exception.AdminUpdateFailedException;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerCreationFailedException;
import africa.springCore.delichopsbackend.infrastructure.exception.*;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    ApiResponse sendInvitationLink(String emailAddress) throws DeliChopsException;

    AdminResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException;

    ApiResponse acceptInvitation(String encryptedLink, AdminInvitationRequest request) throws DeliChopsException;

    ApiResponse validateToken(String token);

    AdminListingDto findAll(Pageable pageable);

    AdminResponseDto findById(Long id) throws AdminNotFoundException, MapperException;

    AdminResponseDto updateAdmin(Long id, AdminUpdateRequest adminUpdateRequest) throws AdminNotFoundException, MapperException, AdminUpdateFailedException, UserNotFoundException, CustomerCreationFailedException;
}
