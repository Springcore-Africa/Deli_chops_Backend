package africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses;

import africa.springCore.delichopsbackend.common.data.BasePageableResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerListingDto extends BasePageableResponse {
    private List<CustomerResponseDto> customers;
}
