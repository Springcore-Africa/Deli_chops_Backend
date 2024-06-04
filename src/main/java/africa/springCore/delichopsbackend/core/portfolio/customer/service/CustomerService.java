package africa.springCore.delichopsbackend.core.portfolio.customer.service;

import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.requests.CustomerCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.requests.CustomerUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerListingDto;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerCreationException;
import africa.springCore.delichopsbackend.core.portfolio.customer.exception.CustomerUpdateException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerCreationRequest customerCreationRequest) throws DeliChopsException, CustomerCreationException;

    CustomerResponseDto findByEmail(String email) throws UserNotFoundException, MapperException;
    CustomerResponseDto findById(Long id) throws UserNotFoundException, MapperException;

    CustomerListingDto retrieveAll(Pageable pageable) throws MapperException;

    CustomerListingDto searchBy(String searchParam, String value, Pageable pageable);

    CustomerResponseDto updateCustomer(Long id, CustomerUpdateRequest customerUpdateRequest) throws CustomerCreationException, UserNotFoundException, MapperException, CustomerUpdateException;
}
