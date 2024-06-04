package africa.springCore.delichopsbackend.core.portfolio.dispatchRider.service;

import africa.springCore.delichopsbackend.core.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.domain.model.DispatchRider;
import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.domain.repository.DispatchRiderRepository;
import africa.springCore.delichopsbackend.core.portfolio.dispatchRider.domain.dtos.responses.DispatchRiderResponseDto;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.springCore.delichopsbackend.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DispatchRiderServiceImpl implements DispatchRiderService {

    private final DeliMapper deliMapper;
    private final DispatchRiderRepository dispatchRiderRepository;

    @Override
    public DispatchRiderResponseDto findByEmail(String emailAddress) throws UserNotFoundException, MapperException {
        DispatchRider foundRider = dispatchRiderRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundRider.getBioData(), BioDataResponseDto.class);
        DispatchRiderResponseDto dispatchRiderResponseDto = deliMapper.readValue(foundRider, DispatchRiderResponseDto.class);
        dispatchRiderResponseDto.setBioData(bioDataResponse);
        return dispatchRiderResponseDto;
    }
}
