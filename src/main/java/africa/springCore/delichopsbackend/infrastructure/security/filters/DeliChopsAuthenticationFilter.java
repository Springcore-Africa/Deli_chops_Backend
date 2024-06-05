package africa.springCore.delichopsbackend.infrastructure.security.filters;

import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.core.base.domain.dtos.request.LoginRequest;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.admin.service.AdminService;
import africa.springCore.delichopsbackend.core.base.service.BioDataService;
import africa.springCore.delichopsbackend.core.portfolio.customer.service.CustomerService;
import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.service.DispatchRiderService;
import africa.springCore.delichopsbackend.core.portfolio.vendor.service.VendorService;
import africa.springCore.delichopsbackend.common.utils.JwtUtility;
import africa.springCore.delichopsbackend.infrastructure.exception.handler.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static africa.springCore.delichopsbackend.common.utils.AppUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@AllArgsConstructor
public class DeliChopsAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtil;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BioDataService bioDataService;
    private final DispatchRiderService dispatchRiderService;
    private final AdminService adminService;
    private final VendorService vendorService;
    private final CustomerService customerService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String email=EMPTY_SPACE_VALUE;
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            System.out.println(email);
            Authentication authResult = authenticationManager.authenticate(authentication);
            System.out.println("Authenticated");
            SecurityContextHolder.getContext().setAuthentication(authResult);
            return authResult;
        }catch (Exception exception){
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            exceptionResponse.setMessage(exception.getMessage());
            exceptionResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                mapper.writeValue(response.getOutputStream(), exceptionResponse);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        String accessToken = jwtUtil.generateAccessToken(authResult.getAuthorities());
        authResult.getDetails();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(ACCESS_TOKEN_VALUE, accessToken);
        String email = (String) authResult.getPrincipal();
        BioDataResponseDto foundBioData = null;
        try {
            foundBioData = bioDataService.findByEmail(email);
        } catch (DeliChopsException e) {
            log.error(e.getMessage());
        }
        try {
            assert foundBioData != null;
            Object foundUser = getActualUser(foundBioData);
            responseData.put(USER, foundUser);
        } catch (UserNotFoundException | MapperException e) {
            log.error("Error during user authentication {}", e.getMessage());
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(
                responseData
        ));
    }

    private Object getActualUser(BioDataResponseDto foundBioData) throws UserNotFoundException, MapperException {
        Object user = null;
        if (foundBioData.getRoles().contains(Role.CUSTOMER)){
            user = customerService.findByEmail(foundBioData.getEmailAddress());
        }else if (foundBioData.getRoles().contains(Role.VENDOR)){
            user = vendorService.findByEmail(foundBioData.getEmailAddress());
        }else if (foundBioData.getRoles().contains(Role.DISPATCH_RIDER)){
            user = dispatchRiderService.findByEmail(foundBioData.getEmailAddress());
        }else if (foundBioData.getRoles().contains(Role.SUPER_ADMIN) || foundBioData.getRoles().contains(Role.ORDINARY_ADMIN)){
            user = adminService.findByEmail(foundBioData.getEmailAddress());
        }
        return user;
    }


}
