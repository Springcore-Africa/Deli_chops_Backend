package africa.springCore.delichopsbackend.security.providers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static africa.springCore.delichopsbackend.common.Message.INVALID_EMAIL_OR_PASSWORD;


@Component
@AllArgsConstructor
@Slf4j
public class DeliChopsAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authResult;
        String email = (String) authentication.getPrincipal();
        String password =  (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String userEmail = userDetails.getUsername();
        String userPassword = userDetails.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("Password matches::>> "+passwordEncoder.matches(password, userPassword));
        if (passwordEncoder.matches(password, userPassword)){
            authResult = new UsernamePasswordAuthenticationToken(userEmail, userPassword, authorities);
            return  authResult;
        }
        throw new BadCredentialsException(INVALID_EMAIL_OR_PASSWORD);
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
