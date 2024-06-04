package africa.springCore.delichopsbackend.core.portfolio.vendor.domain.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendorRegistrationDocumentCreationRequest {

    private String name;

    private String referenceUrl;
}
