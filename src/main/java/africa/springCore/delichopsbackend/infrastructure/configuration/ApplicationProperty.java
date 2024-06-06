package africa.springCore.delichopsbackend.infrastructure.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;

@ConfigurationProperties("deli.chops")
@Configuration
@Data
@EnableJpaAuditing
public class ApplicationProperty {

    private String jwtSigningSecret;
    private String adminInvitationClientUrl;
    private BigDecimal priceInterest;

}
