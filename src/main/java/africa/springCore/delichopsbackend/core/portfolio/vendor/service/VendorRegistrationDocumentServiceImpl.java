package africa.springCore.delichopsbackend.core.portfolio.vendor.service;

import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorRegistrationDocumentCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorRegistrationDocumentListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.Vendor;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.VendorRegistrationDocument;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.repository.VendorRegistrationDocumentRepository;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.repository.VendorRepository;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorCreationException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorUpdateException;
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
import static africa.springCore.delichopsbackend.common.utils.AppUtils.EMAIL_VALUE;
import static africa.springCore.delichopsbackend.common.utils.AppUtils.PHONE_NUMBER;

@Service
@RequiredArgsConstructor
public class VendorRegistrationDocumentServiceImpl implements VendorRegistrationDocumentService {

    private final DeliMapper deliMapper;
    private final VendorRegistrationDocumentRepository documentRepository;


    @Override
    public VendorRegistrationDocument postVendorRegistrationDocument(Long vendorId, VendorRegistrationDocumentCreationRequest documentCreationRequest) throws MapperException {
        VendorRegistrationDocument vendorRegistrationDocument = deliMapper.readValue(documentCreationRequest, VendorRegistrationDocument.class);
        vendorRegistrationDocument.setVendorId(vendorId);
        return documentRepository.save(vendorRegistrationDocument);
    }

    @Override
    public VendorRegistrationDocumentListingDto getVendorRegistrationDocuments(Long vendorId, Pageable pageable) {
        Page<VendorRegistrationDocument> pagedDocuments = documentRepository.findByVendorId(vendorId, pageable);
        VendorRegistrationDocumentListingDto documentListingDto = new VendorRegistrationDocumentListingDto();
        documentListingDto.setPageNumber(pagedDocuments.getNumber());
        documentListingDto.setPageSize(pagedDocuments.getSize());
        documentListingDto.setVendorRegistrationDocuments(pagedDocuments.getContent());
        return documentListingDto;
    }
}
