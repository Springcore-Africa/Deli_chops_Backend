package africa.springCore.delichopsbackend.dtos.responses;

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
