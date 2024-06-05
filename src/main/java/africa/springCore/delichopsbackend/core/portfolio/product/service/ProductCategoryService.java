package africa.springCore.delichopsbackend.core.portfolio.product.service;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.request.ProductCategoryCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductCategoryListingDto;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductCategoryResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductCategoryNotFoundException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {
    ProductCategoryResponseDto findById(Long categoryId) throws ProductCategoryNotFoundException, MapperException;

    ProductCategoryResponseDto postAProductCategory(ProductCategoryCreationRequest productCategoryCreationRequest) throws MapperException;

    ProductCategoryResponseDto findByName(String categoryName) throws ProductCategoryNotFoundException, MapperException;

    ProductCategoryListingDto searchByName(String name, Pageable pageable) throws MapperException;

    ProductCategoryListingDto getAllProductCategories(Pageable pageable);
}
