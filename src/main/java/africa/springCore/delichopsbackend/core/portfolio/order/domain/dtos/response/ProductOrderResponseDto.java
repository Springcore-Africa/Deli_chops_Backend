package africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.response;

import africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response.ProductResponseDto;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductOrderResponseDto {

    private Long id;

    private ProductResponseDto product;

    private Long quantity;

    private BigDecimal price;

}
