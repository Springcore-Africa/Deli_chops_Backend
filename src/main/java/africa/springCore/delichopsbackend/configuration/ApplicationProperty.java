package africa.springCore.delichopsbackend.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("deli.chops")
@Configuration
@Data
public class ApplicationProperty {

    private String jwtSigningSecret;

}
