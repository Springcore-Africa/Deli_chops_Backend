package africa.springCore.delichopsbackend.core.base.domain.dtos.response;

import africa.springCore.delichopsbackend.common.data.BasePageableResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddressListingDto extends BasePageableResponse {
    private List<AddressResponseDto> addresses;
}
