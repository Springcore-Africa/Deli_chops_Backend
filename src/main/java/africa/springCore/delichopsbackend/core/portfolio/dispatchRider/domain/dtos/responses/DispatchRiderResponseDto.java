package africa.springCore.delichopsbackend.core.portfolio.dispatchRider.domain.dtos.responses;

import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DispatchRiderResponseDto {

    private Long id;
    private BioDataResponseDto bioData;
}
