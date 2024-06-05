package africa.springCore.delichopsbackend.core.portfolio.customer.api;


import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.requests.CustomerCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.requests.CustomerUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerListingDto;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerCreationFailedException;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerUpdateException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/customers")
@RestController
@RequiredArgsConstructor
@Validated
public class CustomerApiResource {

    private final CustomerService customerService;

    @Operation(summary = "Create a New Customer")
    @PostMapping("")
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerCreationRequest customerCreationRequest) throws DeliChopsException, CustomerCreationFailedException {

        CustomerResponseDto postClientsResponse =
                customerService.createCustomer(customerCreationRequest);

        return ResponseEntity.ok(postClientsResponse);
    }

    @Operation(summary = "Find by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findById(
            @PathVariable(name = "id") Long id
    ) throws MapperException, UserNotFoundException {

        CustomerResponseDto customer =
                customerService.findById(id);

        return ResponseEntity.ok(customer);
    }


    @Operation(summary = "Find by Email")
    @GetMapping("/search")
    public ResponseEntity<CustomerListingDto> findByEmail(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "searchParam", defaultValue = "email") String searchParam,
            @RequestParam(name = "value") String value
    ) throws MapperException {

        CustomerListingDto customers =
                customerService.searchBy(searchParam, value, pageable);

        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Retrieve all customers")
    @GetMapping("")
    public ResponseEntity<CustomerListingDto> retrieveAll(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) throws MapperException {

        CustomerListingDto customers =
                customerService.retrieveAll(pageable);

        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Update a customer")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @Valid @PathVariable(name = "id") Long id,
            @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest
    ) throws DeliChopsException, CustomerCreationFailedException, CustomerUpdateException {
        {
            CustomerResponseDto customer =
                    customerService.updateCustomer(id, customerUpdateRequest);

            return ResponseEntity.ok(customer);
        }
    }
}