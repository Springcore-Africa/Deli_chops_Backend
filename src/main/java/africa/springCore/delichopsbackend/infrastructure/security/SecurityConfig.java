package africa.springCore.delichopsbackend.infrastructure.security;

import africa.springCore.delichopsbackend.infrastructure.security.filters.DeliChopsAuthorizationFilter;
import africa.springCore.delichopsbackend.infrastructure.exception.handler.CustomAuthenticationFailureHandler;
import africa.springCore.delichopsbackend.infrastructure.security.filters.DeliChopsAuthenticationFilter;
import africa.springCore.delichopsbackend.core.service.service.AdminService;
import africa.springCore.delichopsbackend.core.service.BioDataService;
import africa.springCore.delichopsbackend.core.portfolio.customer.service.CustomerService;
import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.service.DispatchRiderService;
import africa.springCore.delichopsbackend.core.portfolio.vendor.service.VendorService;
import africa.springCore.delichopsbackend.common.utils.JwtUtility;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static africa.springCore.delichopsbackend.common.utils.SecurityUtils.LOGIN_ENDPOINT;
import static africa.springCore.delichopsbackend.common.utils.SecurityUtils.getAuthWhiteList;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final JwtUtility jwtUtil;
    private final BioDataService bioDataService;
    private final DispatchRiderService dispatchRiderService;
    private final AdminService adminService;
    private final VendorService vendorService;
    private final CustomerService customerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new DeliChopsAuthenticationFilter(
                authenticationManager, jwtUtil, bioDataService, dispatchRiderService,
                adminService, vendorService, customerService
        );

        authenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new DeliChopsAuthorizationFilter(jwtUtil), DeliChopsAuthenticationFilter.class)
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(
//                        exceptionHandler -> exceptionHandler
//                                .authenticationEntryPoint(customAuthenticationFailureHandler::onAuthenticationFailure)
//                )
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.sendRedirect("/error");
                                })
                )
                .authorizeHttpRequests(c -> c
                        .requestMatchers(getAuthWhiteList())
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .build();
    }



    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOriginPatterns("*") // Changed from allowedOrigins to allowedOriginPatterns
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders(
                                "Access-Control-Allow-Origin",
                                "Access-Control-Allow-Methods",
                                "Access-Control-Allow-Headers"
                        )
                        .allowCredentials(true);
            }
        };
    }

}
