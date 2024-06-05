package africa.springCore.delichopsbackend.infrastructure.security.clmUser;

import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.core.base.domain.repository.BioDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import static africa.springCore.delichopsbackend.common.Message.INVALID_EMAIL_OR_PASSWORD;

@AllArgsConstructor
@Repository
public class DeliChopsUserService implements UserDetailsService {
    private final BioDataRepository bioDataRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BioData user = bioDataRepository.findByEmailAddress(username).orElseThrow(
                () ->
                        new UsernameNotFoundException(
                                INVALID_EMAIL_OR_PASSWORD
                        ));
        return new DeliChopsUser(user);
    }
}
