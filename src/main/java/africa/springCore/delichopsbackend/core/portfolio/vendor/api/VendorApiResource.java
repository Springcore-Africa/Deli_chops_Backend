package africa.springCore.delichopsbackend.core.portfolio.vendor.api;

import africa.springCore.delichopsbackend.core.portfolio.customer.domain.repository.CustomerRepository;
import africa.springCore.delichopsbackend.core.portfolio.customer.service.CustomerService;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorApprovalFailedException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorCreationException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorUpdateException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/vendors")
@RestController
@RequiredArgsConstructor
@Validated
public class VendorApiResource {

    private final VendorService vendorService;

    @Operation(summary = "Create a New Vendor")
    @PostMapping("")
    public ResponseEntity<VendorResponseDto> createVendor(
            @Valid @RequestBody VendorCreationRequest vendorCreationRequest) throws DeliChopsException, VendorCreationException {

        VendorResponseDto postClientsResponse =
                vendorService.createVendor(vendorCreationRequest);

        return ResponseEntity.ok(postClientsResponse);
    }

    @Operation(summary = "Find by ID")
    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> findById(
            @PathVariable(name = "id") Long id
    ) throws MapperException, UserNotFoundException {

        VendorResponseDto vendor =
                vendorService.findById(id);

        return ResponseEntity.ok(vendor);
    }


    @Operation(summary = "Find by Email")
    @GetMapping("/search")
    public ResponseEntity<VendorListingDto> findByEmail(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "searchParam", defaultValue = "email") String searchParam,
            @RequestParam(name = "value") String value
    ) throws MapperException {

        VendorListingDto vendors =
                vendorService.searchBy(searchParam, value, pageable);

        return ResponseEntity.ok(vendors);
    }

    @Operation(summary = "Retrieve all Vendors")
    @GetMapping("")
    public ResponseEntity<VendorListingDto> retrieveAll(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) throws MapperException {

        VendorListingDto vendors =
                vendorService.retrieveAll(pageable);

        return ResponseEntity.ok(vendors);
    }

    @Operation(summary = "Update a Vendor")
    @PatchMapping("/{id}")
    public ResponseEntity<VendorResponseDto> updateVendor(
            @Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody VendorUpdateRequest VendorUpdateRequest
    ) throws DeliChopsException, VendorCreationException, VendorUpdateException {
        {
            VendorResponseDto vendor =
                    vendorService.updateVendor(id, VendorUpdateRequest);

            return ResponseEntity.ok(vendor);
        }
    }

    @Operation(summary = "Approve or reject a Vendor")
    @PostMapping("/{id}/approval")
    public ResponseEntity<VendorResponseDto> approveVendor(
            @Valid @PathVariable(name = "id") Long id,
            @RequestParam(name = "actionName", defaultValue = "reject") String actionName
    ) throws UserNotFoundException, MapperException, VendorApprovalFailedException {
        {
            VendorResponseDto vendor =
                    vendorService.approveVendor(id, actionName);

            return ResponseEntity.ok(vendor);
        }
    }
}
