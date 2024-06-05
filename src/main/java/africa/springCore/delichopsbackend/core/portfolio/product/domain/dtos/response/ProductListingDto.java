package africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.response;

import africa.springCore.delichopsbackend.common.data.BasePageableResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductListingDto extends BasePageableResponse {
    private List<ProductResponseDto> products;
}
