package africa.springCore.delichopsbackend.services.dispatchRiderServices;

import africa.springCore.delichopsbackend.data.models.Customer;
import africa.springCore.delichopsbackend.data.models.DispatchRider;
import africa.springCore.delichopsbackend.data.repositories.DispatchRiderRepository;
import africa.springCore.delichopsbackend.data.repositories.VendorRepository;
import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.DispatchRiderResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
