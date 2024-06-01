package africa.springCore.delichopsbackend.services.customerServices;

import africa.springCore.delichopsbackend.data.models.BioData;
import africa.springCore.delichopsbackend.data.models.Customer;
import africa.springCore.delichopsbackend.data.repositories.CustomerRepository;
import africa.springCore.delichopsbackend.dtos.requests.CustomerCreationRequest;
import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.CustomerListingDto;
import africa.springCore.delichopsbackend.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.enums.Role;
import africa.springCore.delichopsbackend.exception.DeliChopsException;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.exception.CustomerCreationException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.springCore.delichopsbackend.common.Message.*;
import static africa.springCore.delichopsbackend.utils.AppUtils.EMAIL_VALUE;
import static africa.springCore.delichopsbackend.utils.AppUtils.PHONE_NUMBER;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final DeliMapper deliMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDto createCustomer(CustomerCreationRequest customerCreationRequest) throws DeliChopsException, CustomerCreationException {
        validateCustomerCreationRequest(customerCreationRequest);
        BioData customerBioData = deliMapper.readValue(customerCreationRequest, BioData.class);
        customerBioData.setRoles(List.of(Role.CUSTOMER));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(customerCreationRequest.getPassword());
        customerBioData.setPassword(encodedPassword);
        Customer customer = new Customer();
        customer.setBioData(customerBioData);
        Customer savedCustomer = customerRepository.save(customer);
        String savedCustomerAsString = deliMapper.writeValueAsString(savedCustomer);
        try {
            return deliMapper.readValue(savedCustomerAsString, CustomerResponseDto.class);
        } catch (Exception ex) {
            throw new DeliChopsException(ex.getMessage());
        }
    }

    private void validateCustomerCreationRequest(CustomerCreationRequest customerCreationRequest) throws CustomerCreationException {
        Optional<Customer> foundCustomerByEmail = customerRepository.findByBioData_EmailAddress(customerCreationRequest.getEmailAddress());
        Optional<Customer> foundCustomerByNumber = customerRepository.findByBioData_PhoneNumber(customerCreationRequest.getPhoneNumber());
        if (foundCustomerByEmail.isPresent()){
            throw new CustomerCreationException(
                    String.format(CUSTOMER_WITH_EMAIL_ALREADY_EXISTS, customerCreationRequest.getEmailAddress())
            );
        }
        else if (foundCustomerByNumber.isPresent()){
            throw new CustomerCreationException(
                    String.format(CUSTOMER_WITH_PHONE_NUMBER_ALREADY_EXISTS, customerCreationRequest.getPhoneNumber())
            );
        }
    }

    @Override
    public CustomerResponseDto findByEmail(String email) throws MapperException, UserNotFoundException {
        Customer foundCustomer = customerRepository.findByBioData_EmailAddress(email).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundCustomer.getBioData(), BioDataResponseDto.class);
        CustomerResponseDto customerResponseDto = deliMapper.readValue(foundCustomer, CustomerResponseDto.class);
        customerResponseDto.setBioData(bioDataResponse);
        return customerResponseDto;
    }

    @Override
    public CustomerResponseDto findById(Long id) throws MapperException, UserNotFoundException {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundCustomer.getBioData(), BioDataResponseDto.class);
        CustomerResponseDto customerResponseDto = deliMapper.readValue(foundCustomer, CustomerResponseDto.class);
        customerResponseDto.setBioData(bioDataResponse);
        return customerResponseDto;
    }

    @Override
    public CustomerListingDto retrieveAll(Pageable pageable) {
        Page<CustomerResponseDto> pagedCustomers = customerRepository.findAll(pageable).map((customer) -> {
            try {
                return deliMapper.readValue(customer, CustomerResponseDto.class);
            } catch (MapperException e) {
                e.printStackTrace();
            }
            return null;
        });
        return getCustomerListingDto(pagedCustomers, pageable);
    }

    @Override
    public CustomerListingDto searchBy(String searchParam, String value, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Customer criteria = new Customer();
        BioData bioData = new BioData();
        if (searchParam.equals(EMAIL_VALUE)) bioData.setEmailAddress(value);
        else if (searchParam.equals(PHONE_NUMBER)) bioData.setPhoneNumber(value);
        criteria.setBioData(bioData);
        Example<Customer> example = Example.of(criteria, matcher);
        Page<CustomerResponseDto> pagedCustomers = customerRepository.findAll(example, pageable).map((customer) -> {
            try {
                return deliMapper.readValue(customer, CustomerResponseDto.class);
            } catch (MapperException e) {
                e.printStackTrace();
            }
            return null;
        });
        return getCustomerListingDto(pagedCustomers, pageable);
    }

    private CustomerListingDto getCustomerListingDto(Page<CustomerResponseDto> pagedCustomers, Pageable pageable) {
        CustomerListingDto customerListingDto = new CustomerListingDto();
        customerListingDto.setCustomers(pagedCustomers.getContent());
        customerListingDto.setPageNumber(pageable.getPageNumber());
        customerListingDto.setPageSize(pageable.getPageSize());
        return customerListingDto;
    }
}
