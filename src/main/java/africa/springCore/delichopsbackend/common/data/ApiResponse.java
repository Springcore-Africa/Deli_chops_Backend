package africa.springCore.delichopsbackend.common.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	private String message;
	private BigDecimal totalOrderAmount;
	private int statusCode;
	private boolean success;
	private Object data;
}
