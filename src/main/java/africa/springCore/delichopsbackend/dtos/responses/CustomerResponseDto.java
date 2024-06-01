package africa.springCore.delichopsbackend.dtos.responses;

import africa.springCore.delichopsbackend.data.models.Customer;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDto {

    private Long id;
    private BioDataResponseDto bioData;

}
