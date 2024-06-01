package africa.springCore.delichopsbackend.services.vendorServices;

import africa.springCore.delichopsbackend.data.models.Vendor;
import africa.springCore.delichopsbackend.data.repositories.VendorRepository;
import africa.springCore.delichopsbackend.dtos.responses.BioDataResponseDto;
import africa.springCore.delichopsbackend.dtos.responses.VendorResponseDto;
import africa.springCore.delichopsbackend.exception.MapperException;
import africa.springCore.delichopsbackend.exception.UserNotFoundException;
import africa.springCore.delichopsbackend.utils.DeliMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static africa.springCore.delichopsbackend.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final DeliMapper deliMapper;
    private final VendorRepository vendorRepository;

    @Override
    public VendorResponseDto findByEmail(String emailAddress) throws MapperException, UserNotFoundException {
        Vendor foundVendor = vendorRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponseDto bioDataResponse = deliMapper.readValue(foundVendor.getBioData(), BioDataResponseDto.class);
        VendorResponseDto vendorResponseDto = deliMapper.readValue(foundVendor, VendorResponseDto.class);
        vendorResponseDto.setBioData(bioDataResponse);
        return vendorResponseDto;
    }
}
