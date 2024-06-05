package africa.springCore.delichopsbackend.core.portfolio.admin.service;

import africa.springCore.delichopsbackend.common.data.ApiResponse;
import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.common.utils.JwtUtility;
import africa.springCore.delichopsbackend.core.base.domain.dtos.response.BioDataResponseDto;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.core.base.service.BioDataService;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.requests.AdminInvitationRequest;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminListingDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.dtos.responses.AdminResponseDto;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.model.Admin;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.repository.AdminRepository;
import africa.springCore.delichopsbackend.core.portfolio.admin.exception.AdminNotFoundException;
import africa.springCore.delichopsbackend.core.portfolio.customer.domain.dtos.responses.CustomerListingDto;
import africa.springCore.delichopsbackend.infrastructure.configuration.ApplicationProperty;
import africa.springCore.delichopsbackend.infrastructure.exception.*;
import africa.springCore.delichopsbackend.common.utils.DeliMapper;
import africa.springCore.delichopsbackend.infrastructure.notification.mailServices.domain.data.Recipient;
import africa.springCore.delichopsbackend.infrastructure.notification.mailServices.domain.dtos.EmailNotificationRequest;
import africa.springCore.delichopsbackend.infrastructure.notification.mailServices.service.MailService;
import com.auth0.jwt.interfaces.Claim;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static africa.springCore.delichopsbackend.common.Message.*;
import static africa.springCore.delichopsbackend.common.utils.AppUtils.*;
import static africa.springCore.delichopsbackend.common.utils.HtmlFileUtility.getFileTemplateFromClasspath;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final DeliMapper deliMapper;
    private final AdminRepository adminRepository;
    private final JwtUtility jwtUtility;
    private final ApplicationProperty applicationProperty;
    private final MailService mailService;
    private final BioDataService bioDataService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse sendInvitationLink(String emailAddress) throws DeliChopsException {
        validateDuplicateUserExistence(emailAddress);
        Map<String, Object> requestAsMap = buildRequestAsMap(emailAddress);
        String encryptedEmail = jwtUtility.generateEncryptedLink(requestAsMap);
        String invitationLink = applicationProperty.getAdminInvitationClientUrl().concat(encryptedEmail);
        Recipient recipient = Recipient.builder().email(emailAddress).build();
        EmailNotificationRequest emailRequest = new EmailNotificationRequest();
        emailRequest.setTo(List.of(recipient));
        emailRequest.setSubject(DELI_CHOPS_ADMIN_INVITATION);
        String template = getFileTemplateFromClasspath(ADMIN_INVITATION_HTML_TEMPLATE_LOCATION);
        System.out.println("Template retrieved");
        emailRequest.setText(String.format(template, invitationLink));
        try {
            mailService.sendHtmlMail(emailRequest);
        } catch (Exception e) {
            log.info("Admin Invitation {}", e.getMessage());
        }
        return apiResponse(MAIL_HAS_BEEN_SENT_SUCCESSFULLY);
    }


    private Map<String, Object> buildRequestAsMap(String emailAddress) {
        Map<String, Object> map = new HashMap<>();
        map.put(EMAIL_VALUE, emailAddress);
        return map;
    }

    @Override
    public ApiResponse acceptInvitation(String encryptedLink, AdminInvitationRequest request) throws DeliChopsException {
        Admin admin = getAdmin(encryptedLink, request);
        if (adminRepository.findByBioData_EmailAddress(admin.getBioData().getEmailAddress()).isPresent()) {
            throw new UserAlreadyExistsException("You have already registered on this platform");
        }
        String randomPassword = jwtUtility.generateAdminDefaultPassword(admin.getBioData().getEmailAddress());
        String encryptedPassword = passwordEncoder.encode(randomPassword);
        admin.getBioData().setPassword(encryptedPassword);
        admin.getBioData().setRoles(List.of(Role.ORDINARY_ADMIN));
        adminRepository.save(admin);
        String template = getFileTemplateFromClasspath(SUCCESSFUL_REGISTRATION_HTML_TEMPLATE_LOCATION);
        template = String.format(template, admin.getBioData().getEmailAddress(), randomPassword);

        Recipient recipient = Recipient.builder().email(admin.getBioData().getEmailAddress()).build();
        EmailNotificationRequest emailRequest = new EmailNotificationRequest();
        emailRequest.setTo(List.of(recipient));
        emailRequest.setSubject(DELI_CHOPS_ADMIN_INVITATION);
        emailRequest.setText(template);
        try {
            mailService.sendHtmlMail(emailRequest);
        } catch (Exception e) {
            log.info("Admin Invitation {}", e.getMessage());
        }
        return apiResponse("Registration is successful. Please, check your mail.");
    }

    @Override
    public ApiResponse validateToken(String token) {
        jwtUtility.validateToken(token);
        return apiResponse(TOKEN_HAS_BEEN_VERIFIED_SUCCESSFULLY);
    }

    @Override
    public AdminListingDto findAll(Pageable pageable) {
        return getAdminListingDto(
                adminRepository.findAll(pageable)
        );
    }

    @Override
    public AdminResponseDto findById(Long id) throws AdminNotFoundException, MapperException {
        return getAdminResponseDto(
                adminRepository.findById(id).orElseThrow(
                        () -> new AdminNotFoundException(String.format(ADMIN_WITH_ID_NOT_FOUND, id))
                )
        );
    }

    private AdminListingDto getAdminListingDto(Page<Admin> admins) {
        Page<AdminResponseDto> responseDtoPage = admins.map(
                admin -> {
                    try {
                        return getAdminResponseDto(admin);
                    } catch (MapperException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        AdminListingDto adminListingDto = new AdminListingDto();
        adminListingDto.setAdmins(responseDtoPage.getContent());
        adminListingDto.setPageNumber(responseDtoPage.getNumber());
        adminListingDto.setPageSize(responseDtoPage.getSize());
        return adminListingDto;
    }

    private AdminResponseDto getAdminResponseDto(Admin admin) throws MapperException {
        return deliMapper.readValue(admin, AdminResponseDto.class);
    }

    private Admin getAdmin(String encryptedLink, AdminInvitationRequest request) throws AuthenticationException, MapperException {
        Claim claim = jwtUtility.extractClaimFrom(encryptedLink, ADMIN);
        Map<String, Object> adminAsMap = claim.asMap();
        BioData bioData = deliMapper.readValue(request, BioData.class);
        bioData.setEmailAddress((String) adminAsMap.get(EMAIL_VALUE));
        Admin admin = new Admin();
        admin.setBioData(bioData);
        return admin;
    }


    private void validateDuplicateUserExistence(String emailAddress) throws UserAlreadyExistsException {
        bioDataService.validateDuplicateUserExistence(emailAddress);
    }


    @Override
    public AdminResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException {
        Admin foundAdmin = adminRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                () -> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundAdmin.getBioData(), BioDataResponseDto.class);
        AdminResponseDto adminResponseDto = deliMapper.readValue(foundAdmin, AdminResponseDto.class);
        adminResponseDto.setBioData(bioDataResponse);
        return adminResponseDto;
    }
}
