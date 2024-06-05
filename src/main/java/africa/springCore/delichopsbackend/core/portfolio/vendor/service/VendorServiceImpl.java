package africa.springCore.delichopsbackend.core.portfolio.vendor.service;

import africa.springCore.delichopsbackend.common.enums.ApprovalStatus;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.core.base.domain.repository.BioDataRepository;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.Vendor;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.repository.VendorRepository;
import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorCreationException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorUpdateException;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
import static africa.springCore.delichopsbackend.common.utils.AppUtils.*;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final DeliMapper deliMapper;
    private final VendorRepository vendorRepository;
    private final BioDataRepository bioDataRepository;

    @Override
    public VendorResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException {
        Vendor foundVendor = vendorRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundVendor.getBioData(), BioDataResponseDto.class);
        VendorResponseDto vendorResponseDto = deliMapper.readValue(foundVendor, VendorResponseDto.class);
        vendorResponseDto.setBioData(bioDataResponse);
        return vendorResponseDto;
    }

    @Override
    public VendorResponseDto createVendor(VendorCreationRequest vendorCreationRequest) throws DeliChopsException, VendorCreationException {
        validateVendorCreationRequest(vendorCreationRequest);
        BioData vendorBioData = deliMapper.readValue(vendorCreationRequest, BioData.class);
        vendorBioData.setRoles(List.of(Role.VENDOR));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(vendorCreationRequest.getPassword());
        vendorBioData.setPassword(encodedPassword);
        Vendor vendor = new Vendor();
        vendor.setBusinessName(vendorCreationRequest.getBusinessName());
        vendor.setBioData(vendorBioData);
        vendor.setApprovalStatus(ApprovalStatus.PENDING_REVIEW);
        Vendor savedVendor = vendorRepository.save(vendor);
        String savedVendorAsString = deliMapper.writeValueAsString(savedVendor);
        try {
            return deliMapper.readValue(savedVendorAsString, VendorResponseDto.class);
        } catch (Exception ex) {
            throw new DeliChopsException(ex.getMessage());
        }
    }

    private void validateVendorCreationRequest(VendorCreationRequest VendorCreationRequest) throws VendorCreationException {
        validateEmailDuplicity(VendorCreationRequest.getEmailAddress());
        if (VendorCreationRequest.getPhoneNumber() != null && !StringUtils.isEmpty(VendorCreationRequest.getPhoneNumber())) {
            validatePhoneNumberDuplicity(VendorCreationRequest.getPhoneNumber());
        }
    }

    private void validateEmailDuplicity(String emailAddress) throws VendorCreationException {
        if (emailAddress == null || StringUtils.isEmpty(emailAddress)) {
            throw new VendorCreationException("Validation failed, emailAddress cannot be null");
        }
        Optional<Vendor> foundVendorByEmail = vendorRepository.findByBioData_EmailAddress(emailAddress);
        Optional<BioData> foundCustomerByEmail = bioDataRepository.findByEmailAddress(emailAddress);
        if (foundVendorByEmail.isPresent()) {
            throw new VendorCreationException(
                    String.format(VENDOR_WITH_EMAIL_ALREADY_EXISTS, emailAddress)
            );
        }
        if (foundCustomerByEmail.isPresent()) {
            throw new VendorCreationException(
                    String.format(USER_WITH_EMAIL_ALREADY_EXISTS, emailAddress)
            );
        }
    }

    private void validatePhoneNumberDuplicity(String phoneNumber) throws VendorCreationException {
        if (phoneNumber == null || StringUtils.isEmpty(phoneNumber)) {
            throw new VendorCreationException("Validation failed, phone number cannot be null");
        }
        Optional<Vendor> foundVendorByNumber = vendorRepository.findByBioData_PhoneNumber(phoneNumber);
        Optional<BioData> foundCustomerByNumber = bioDataRepository.findByEmailAddress(phoneNumber);
        if (foundVendorByNumber.isPresent()) {
            throw new VendorCreationException(
                    String.format(VENDOR_WITH_PHONE_NUMBER_ALREADY_EXISTS, phoneNumber)
            );
        }
        if (foundCustomerByNumber.isPresent()) {
            throw new VendorCreationException(
                    String.format(USER_WITH_PHONE_NUMBER_ALREADY_EXISTS, phoneNumber)
            );
        }
    }

    private VendorResponseDto getVendorResponseDto(Vendor foundVendor) throws MapperException {
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundVendor.getBioData(), BioDataResponseDto.class);
        VendorResponseDto VendorResponseDto = deliMapper.readValue(foundVendor, VendorResponseDto.class);
        VendorResponseDto.setBioData(bioDataResponse);
        return VendorResponseDto;
    }

    @Override
    public VendorResponseDto findById(Long id) throws MapperException, UserNotFoundException {
        Vendor foundVendor = vendorRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id))
        );
        return getVendorResponseDto(foundVendor);
    }

    @Override
    public VendorListingDto retrieveAll(Pageable pageable) {
        Page<VendorResponseDto> pagedVendors = vendorRepository.findAll(pageable).map((vendor) -> {
            try {
                return deliMapper.readValue(vendor, VendorResponseDto.class);
            } catch (MapperException e) {
                e.printStackTrace();
            }
            return null;
        });
        return getVendorListingDto(pagedVendors, pageable);
    }

    @Override
    public VendorListingDto searchBy(String searchParam, String value, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Vendor criteria = new Vendor();
        BioData bioData = new BioData();
        if (searchParam.equals(EMAIL_VALUE)) bioData.setEmailAddress(value);
        else if (searchParam.equals(PHONE_NUMBER)) bioData.setPhoneNumber(value);
        else if (searchParam.equals(FIRST_NAME)) bioData.setFirstName(value);
        else if (searchParam.equals(LAST_NAME)) bioData.setLastName(value);
        criteria.setBioData(bioData);
        Example<Vendor> example = Example.of(criteria, matcher);
        Page<VendorResponseDto> pagedVendors = vendorRepository.findAll(example, pageable).map((vendor) -> {
            try {
                return deliMapper.readValue(vendor, VendorResponseDto.class);
            } catch (MapperException e) {
                e.printStackTrace();
            }
            return null;
        });
        return getVendorListingDto(pagedVendors, pageable);
    }

    @Override
    public VendorResponseDto updateVendor(Long id, VendorUpdateRequest vendorUpdateRequest) throws VendorCreationException, UserNotFoundException, MapperException, VendorUpdateException {
        boolean allFieldsAreEmpty = true;
        findById(id);
        Vendor existingVendor = vendorRepository.findById(id).get();
        BioData existingVendorBioData = vendorRepository.findById(id).get().getBioData();
        if (vendorUpdateRequest.getEmailAddress() != null && !StringUtils.isEmpty(vendorUpdateRequest.getEmailAddress())) {
            allFieldsAreEmpty = false;
            validateEmailDuplicity(vendorUpdateRequest.getEmailAddress());
            existingVendorBioData.setEmailAddress(vendorUpdateRequest.getEmailAddress());
        }
        if (vendorUpdateRequest.getPhoneNumber() != null && !StringUtils.isEmpty(vendorUpdateRequest.getPhoneNumber())) {
            allFieldsAreEmpty = false;
            validatePhoneNumberDuplicity(vendorUpdateRequest.getPhoneNumber());
            existingVendorBioData.setPhoneNumber(vendorUpdateRequest.getPhoneNumber());
        }
        if (vendorUpdateRequest.getFirstName() != null && !StringUtils.isEmpty(vendorUpdateRequest.getFirstName())) {
            allFieldsAreEmpty = false;
            existingVendorBioData.setFirstName(vendorUpdateRequest.getFirstName());
        }
        if (vendorUpdateRequest.getLastName() != null && !StringUtils.isEmpty(vendorUpdateRequest.getLastName())) {
            allFieldsAreEmpty = false;
            existingVendorBioData.setLastName(vendorUpdateRequest.getLastName());
        }
        if (vendorUpdateRequest.getProfilePicture() != null && !StringUtils.isEmpty(vendorUpdateRequest.getProfilePicture())) {
            allFieldsAreEmpty = false;
            existingVendorBioData.setProfilePicture(vendorUpdateRequest.getProfilePicture());
        }

        if (allFieldsAreEmpty) throw new VendorUpdateException("No field specified for update");
        else {
            existingVendor.setBioData(existingVendorBioData);
            return getVendorResponseDto(vendorRepository.save(existingVendor));
        }
    }

    @Override
    public VendorResponseDto approveVendor(Long id, String actionName) throws UserNotFoundException, MapperException {
        findById(id);
        Vendor existingVendor = vendorRepository.findById(id).get();
        if (actionName.equalsIgnoreCase(APPROVE)) existingVendor.setApprovalStatus(ApprovalStatus.APPROVED);
        if (actionName.equalsIgnoreCase(REJECT)) existingVendor.setApprovalStatus(ApprovalStatus.REJECTED);
        return getVendorResponseDto(vendorRepository.save(existingVendor));
    }

    private VendorListingDto getVendorListingDto(Page<VendorResponseDto> pagedVendors, Pageable pageable) {
        VendorListingDto vendorListingDto = new VendorListingDto();
        vendorListingDto.setVendors(pagedVendors.getContent());
        vendorListingDto.setPageNumber(pageable.getPageNumber());
        vendorListingDto.setPageSize(pageable.getPageSize());
        return vendorListingDto;
    }
}
