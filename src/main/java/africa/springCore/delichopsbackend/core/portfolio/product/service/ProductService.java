package africa.springCore.delichopsbackend.core.portfolio.product.service;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.request.ProductCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductListingDto;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductCategoryNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductCreationFailedException;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductNotFoundException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDto postAProduct(Long vendorId, ProductCreationRequest productCreationRequest) throws UserNotFoundException, MapperException, ProductCategoryNotFoundException, ProductCreationFailedException;

    ProductListingDto getAllProducts(Pageable pageable);

    ProductResponseDto getProductById(Long id) throws ProductNotFoundException, MapperException, ProductCategoryNotFoundException;

    ProductListingDto getVendorProducts(Long vendorId, Pageable pageable);

    ProductListingDto searchProducts(String searchParam, String value, Pageable pageable) throws ProductCategoryNotFoundException, MapperException;
}
