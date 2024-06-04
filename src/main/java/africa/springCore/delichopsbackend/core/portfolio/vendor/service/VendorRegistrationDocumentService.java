package africa.springCore.delichopsbackend.core.portfolio.vendor.service;

import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorRegistrationDocumentCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests.VendorUpdateRequest;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorRegistrationDocumentListingDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.vendor.domain.model.VendorRegistrationDocument;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorCreationException;
import africa.springCore.delichopsbackend.core.portfolio.vendor.exception.VendorUpdateException;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

public interface VendorRegistrationDocumentService {

    VendorRegistrationDocument postVendorRegistrationDocument(Long vendorId, VendorRegistrationDocumentCreationRequest documentCreationRequest) throws MapperException;

    VendorRegistrationDocumentListingDto getVendorRegistrationDocuments(Long vendorId, Pageable pageable);
}
