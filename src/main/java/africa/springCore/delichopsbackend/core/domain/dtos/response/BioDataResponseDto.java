package africa.springCore.delichopsbackend.core.domain.dtos.response;

import africa.springCore.delichopsbackend.common.enums.Role;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BioDataResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String profilePicture;
    private List<Role> roles;
    private Boolean isEnabled;
}
