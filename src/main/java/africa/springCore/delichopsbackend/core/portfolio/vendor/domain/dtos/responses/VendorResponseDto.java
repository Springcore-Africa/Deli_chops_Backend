package africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.responses;

import africa.springCore.delichopsbackend.common.enums.ApprovalStatus;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendorResponseDto {

    private Long id;
    private BioDataResponseDto bioData;
    private String businessName;

    private ApprovalStatus approvalStatus;
}
