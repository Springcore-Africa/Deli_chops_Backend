package africa.springCore.delichopsbackend.services.bioDataServices;

import africa.springCore.delichopsbackend.data.models.BioData;
import africa.springCore.delichopsbackend.data.repositories.BioDataRepository;
import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static africa.springCore.delichopsbackend.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BioDataServiceImpl implements BioDataService {

    private final DeliMapper deliMapper;
    private final BioDataRepository bioDataRepository;

    @Override
    public BioDataResponseDto findByEmail(String email) throws UserNotFoundException, MapperException {
        BioData foundBioData = bioDataRepository.findByEmailAddress(email).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))
        );
        return deliMapper.readValue(foundBioData, BioDataResponseDto.class);
    }

}
