package africa.springCore.delichopsbackend.core.portfolio.customer.api;

import africa.springCore.delichopsbackend.core.base.domain.dtos.request.AddressCreationRequest;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.AddressListingDto;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.AddressResponseDto;
import africa.springCore.delichopsbackend.core.base.service.AddressService;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerCreationException;
import africa.springCore.delichopsbackend.core.portfolio.customer.service.CustomerService;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static africa.springCore.delichopsbackend.common.enums.Role.CUSTOMER;

@RequestMapping("api/v1/customers/{customerId}/address")
@RestController
@RequiredArgsConstructor
@Validated
public class CustomerAddressApiResource {

    private final AddressService addressService;
    private final CustomerService customerService;

    @Operation(summary = "Create a new Address for a customer")
    @PostMapping("")
    public ResponseEntity<AddressResponseDto> createCustomerAddress(
            @PathVariable(name = "customerId") String customerId,
            @RequestBody AddressCreationRequest addressCreationRequest) throws DeliChopsException, CustomerCreationException {
        customerService.findById(Long.valueOf(customerId));
        AddressResponseDto postAddressesResponse =
                addressService.createUserAddress(addressCreationRequest, CUSTOMER, Long.valueOf(customerId));

        return ResponseEntity.ok(postAddressesResponse);
    }

    @Operation(summary = "Find by address ID")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findById(
            @PathVariable(name = "customerId") Long customerId,
            @PathVariable(name = "id") Long id) throws DeliChopsException {

        customerService.findById(customerId);
        AddressResponseDto postAddressesResponse =
                addressService.findById(id, CUSTOMER);

        return ResponseEntity.ok(postAddressesResponse);
    }

    @Operation(summary = "Find Customer id by ID")
    @GetMapping("")
    public ResponseEntity<AddressListingDto> findByCustomerId(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(name = "customerId") Long customerId) throws DeliChopsException {

        customerService.findById(customerId);
        AddressListingDto postAddressesResponse =
                addressService.findByUserId(customerId, CUSTOMER, pageable);

        return ResponseEntity.ok(postAddressesResponse);
    }

    @Operation(summary = "Update customer address")
    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateCustomer(
            @PathVariable(name = "customerId") Long customerId,
            @PathVariable(name = "addressId") Long addressId,
            @Valid @RequestBody AddressCreationRequest addressCreationRequest
    ) throws DeliChopsException {

        customerService.findById(customerId);
        AddressResponseDto postAddressesResponse =
                addressService.updateUserAddress(addressCreationRequest, customerId, CUSTOMER, addressId);

        return ResponseEntity.ok(postAddressesResponse);
    }
}
