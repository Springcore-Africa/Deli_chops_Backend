package africa.springCore.delichopsbackend.core.portfolio.admin.api;

import africa.springCore.delichopsbackend.common.data.ApiResponse;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.requests.AdminInvitationRequest;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.requests.AdminUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminListingDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.exception.AdminNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.admin.exception.AdminUpdateFailedException;
import africa.springCore.delichopsbackend.core.portfolio.admin.service.AdminService;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerCreationFailedException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/admin")
@RestController
@RequiredArgsConstructor
@Validated
public class AdminApiResource {
    private final AdminService adminService;

    @Operation(
            summary = "Send Invitation Link",
            description = "API for sending an invitation link to an admin."
    )
    @PostMapping("/sendInvitationLink/{emailAddress}")
    public ResponseEntity<ApiResponse> sendInvitationLink(
            @PathVariable(name = "emailAddress") String emailAddress
    ) throws DeliChopsException {
        return ResponseEntity.ok().body(adminService.sendInvitationLink(emailAddress));
    }

    @Operation(summary = "Validate Invitation Token link")
    @PostMapping("/validateToken/{token}")
    public ResponseEntity<ApiResponse> validateToken(
            @PathVariable(name = "token") String token
    ) throws DeliChopsException {
        return ResponseEntity.ok().body(adminService.validateToken(token));
    }


    @Operation(summary = "Find all")
    @GetMapping("")
    public ResponseEntity<AdminListingDto> findAll(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) throws DeliChopsException {
        return ResponseEntity.ok().body(adminService.findAll(pageable));
    }


    @Operation(summary = "Find by id")
    @GetMapping("/findById/{id}")
    public ResponseEntity<AdminResponseDto> findById(
            @PathVariable(name = "id") Long id
    ) throws DeliChopsException, AdminNotFoundException {
        return ResponseEntity.ok().body(adminService.findById(id));
    }


    @Operation(summary = "Find by email")
    @GetMapping("/findByEmail/{id}")
    public ResponseEntity<AdminResponseDto> findByEmail(
            @PathVariable(name = "email") String email
    ) throws DeliChopsException, AdminNotFoundException {
        return ResponseEntity.ok().body(adminService.findByEmail(email));
    }


    @Operation(summary = "Update an admin")
    @PatchMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(
            @PathVariable(name = "id") Long id,
            @RequestBody AdminUpdateRequest adminUpdateRequest
    ) throws UserNotFoundException, AdminUpdateFailedException, CustomerCreationFailedException, AdminNotFoundException, MapperException {
        return ResponseEntity.ok().body(adminService.updateAdmin(id, adminUpdateRequest));
    }


    @Operation(
            summary = "Accept Invitation Link",
            description = "API for accepting an invitation link to an admin."
    )
    @PostMapping("acceptInvitation/{encryptedLink}")
    public ResponseEntity<ApiResponse> acceptInvitation(
            @PathVariable(name = "encryptedLink") String encryptedLink,
            @Valid @RequestBody AdminInvitationRequest request
    ) throws DeliChopsException {
        return ResponseEntity.ok().body(adminService.acceptInvitation(encryptedLink, request));
    }
}
