package africa.springCore.delichopsbackend.core.portfolio.order.domain.dtos.response;

import africa.springCore.delichopsbackend.common.data.BasePageableResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderListingDto extends BasePageableResponse {
    private List<OrderResponseDto> orders;
}
