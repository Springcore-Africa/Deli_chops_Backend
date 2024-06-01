package africa.springCore.delichopsbackend.services.customerServices;

import africa.springCore.delichopsbackend.dtos.requests.CustomerCreationRequest;
import africa.springCore.delichopsbackend.dtos.responses.CustomerListingDto;
import africa.springCore.delichopsbackend.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.exception.DeliChopsException;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.exception.CustomerCreationException;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerCreationRequest customerCreationRequest) throws DeliChopsException, CustomerCreationException;

    CustomerResponseDto findByEmail(String email) throws UserNotFoundException, MapperException;
    CustomerResponseDto findById(Long id) throws UserNotFoundException, MapperException;

    CustomerListingDto retrieveAll(Pageable pageable) throws MapperException;

    CustomerListingDto searchBy(String searchParam, String value, Pageable pageable);
}
