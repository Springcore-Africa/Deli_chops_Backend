package africa.springCore.delichopsbackend.services.adminServices;

import africa.springCore.delichopsbackend.data.models.Admin;
import africa.springCore.delichopsbackend.data.models.Customer;
import africa.springCore.delichopsbackend.data.repositories.AdminRepository;
import africa.springCore.delichopsbackend.data.repositories.DispatchRiderRepository;
import africa.springCore.delichopsbackend.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.CustomerResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static africa.springCore.delichopsbackend.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final DeliMapper deliMapper;
    private final AdminRepository adminRepository;

    @Override
    public AdminResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException {
        Admin foundAdmin = adminRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundAdmin.getBioData(), BioDataResponseDto.class);
        AdminResponseDto adminResponseDto = deliMapper.readValue(foundAdmin, AdminResponseDto.class);
        adminResponseDto.setBioData(bioDataResponse);
        return adminResponseDto;
    }
}
