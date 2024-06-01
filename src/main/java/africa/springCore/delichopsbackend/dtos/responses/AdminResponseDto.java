package africa.springCore.delichopsbackend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminResponseDto {

    private Long id;
    private BioDataResponseDto bioData;
}
