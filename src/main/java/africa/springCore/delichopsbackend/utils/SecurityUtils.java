package africa.springCore.delichopsbackend.utils;

public class SecurityUtils {

    public static final String LOGIN_ENDPOINT = "/api/v1/login";

    public static String[] getAuthWhiteList() {
        return new String[]{
                "/api/v1/customers",
                "/api/v1/health-check",
                "/api/v1/customers/**",
                LOGIN_ENDPOINT,
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }

}
