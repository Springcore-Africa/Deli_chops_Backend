package africa.springCore.delichopsbackend.core.portfolio.product.domain.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductCategoryCreationRequest {

    @NotBlank(message = "name is mandatory")
    private String name;
}
