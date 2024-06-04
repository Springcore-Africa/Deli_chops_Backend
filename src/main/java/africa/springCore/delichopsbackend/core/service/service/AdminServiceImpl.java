package africa.springCore.delichopsbackend.core.service.service;

import africa.springCore.delichopsbackend.core.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.model.Admin;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.repository.AdminRepository;
import africa.springCore.delichopsbackend.infrastructure.exception.MapperException;
import africa.springCore.delichopsbackend.infrastructure.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
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
