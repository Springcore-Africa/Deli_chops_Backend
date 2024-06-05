package africa.springCore.delichopsbackend.common.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
	private String message;
	private int statusCode;
	private boolean success;
	private Object data;
}
