package africa.springCore.delichopsbackend.core.base.service;

import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.base.domain.repository.BioDataRepository;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserAlreadyExistsException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.springCore.delichopsbackend.common.Message.*;

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


    @Override
    public void validateDuplicateUserExistence(String emailAddress) throws UserAlreadyExistsException {
        var bioData = bioDataRepository.findByEmailAddress(emailAddress);
        boolean accountWithGivenEmailAlreadyExist = bioData.isPresent();
        if (accountWithGivenEmailAlreadyExist) throw new UserAlreadyExistsException(String.format(ACCOUNT_ALREADY_EXIST, emailAddress));
    }

    @Override
    public BioDataResponseDto findByPhoneNumber(String phoneNumber) throws UserNotFoundException, MapperException {
        BioData foundBioData = bioDataRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_PHONE_NUMBER_NOT_FOUND, phoneNumber))
        );
        return deliMapper.readValue(foundBioData, BioDataResponseDto.class);
    }

}
