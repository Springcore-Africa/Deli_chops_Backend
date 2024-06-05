package africa.springCore.delichopsbackend.core.portfolio.product.api;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.request.ProductCategoryCreationRequest;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductCategoryListingDto;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductCategoryResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.product.exception.ProductCategoryNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.product.service.ProductCategoryService;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/products/categories")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductCategoryApiResource {


    private final ProductCategoryService productCategoryService;

    @PostMapping("")
    @Operation(summary = "Create a New Product Category")
    public ResponseEntity<ProductCategoryResponseDto> postAProductCategory(
            @Valid @RequestBody ProductCategoryCreationRequest productCategoryCreationRequest
    ) throws MapperException {
        ProductCategoryResponseDto postProductCategoryResponse = productCategoryService.postAProductCategory(productCategoryCreationRequest);
        return ResponseEntity.ok(postProductCategoryResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a Product Category by ID")
    public ResponseEntity<ProductCategoryResponseDto> getAProductCategoryId(
            @PathVariable(name = "id") Long id
    ) throws ProductCategoryNotFoundException, MapperException {
        ProductCategoryResponseDto postProductCategoryResponse = productCategoryService.findById(id);
        return ResponseEntity.ok(postProductCategoryResponse);
    }

    @GetMapping("")
    @Operation(summary = "Find all Product Categories")
    public ResponseEntity<ProductCategoryListingDto> getAllProductCategories(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) throws ProductCategoryNotFoundException, MapperException {
        ProductCategoryListingDto postProductCategoryResponse = productCategoryService.getAllProductCategories(pageable);
        return ResponseEntity.ok(postProductCategoryResponse);
    }
}
