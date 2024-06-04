package africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerListingDto {
    private int pageNumber;
    private int pageSize;
    private List<CustomerResponseDto> customers;
}
