package africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response;

import africa.springCore.delichopsbackend.core.base.domain.model.BaseEntity;
import africa.springCore.delichopsbackend.core.portfolio.product.domain.model.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    private Long vendorId;

    private String name;

    private String description;

    private String picture;

    private ProductCategoryResponseDto category;

    private BigDecimal price;

    private Long quantity;

    private BigDecimal priceInterestInPercentage;
}
