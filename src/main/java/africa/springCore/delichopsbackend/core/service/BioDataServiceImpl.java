package africa.springCore.delichopsbackend.core.service;

import africa.springCore.delichopsbackend.core.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.domain.model.BioData;
import africa.springCore.delichopsbackend.core.domain.repository.BioDataRepository;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
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
