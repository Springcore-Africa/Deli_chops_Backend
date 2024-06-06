package africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.request;

import africa.springCore.delichopsbackend.core.portfolio.order.domain.model.ProductOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
public class ProductOrderCreationRequest {

    @NotNull(message = "productId is mandatory")
    private Long productId;

    @NotNull(message = "quantity is mandatory")
    private Long quantity;
}
