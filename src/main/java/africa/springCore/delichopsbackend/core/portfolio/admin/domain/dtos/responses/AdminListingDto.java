package africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses;

import africa.springCore.delichopsbackend.common.data.BasePageableResponse;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AdminListingDto extends BasePageableResponse {
    private List<AdminResponseDto> admins;
}
