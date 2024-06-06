package africa.springCore.delichopsbackend.core.portfolio.product.domain.repository;

import africa.springCore.delichopsbackend.common.enums.Role;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.model.Admin;
import africa.springCore.delichopsbackend.core.portfolio.admin.domain.repository.AdminRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void test() {
        BioData bioData = new BioData();
        bioData.setEmailAddress("ogunsmoyin.m@gmail.com");
        bioData.setPassword(passwordEncoder.encode("password"));
        bioData.setFirstName("Deli");
        bioData.setLastName("Chops");
        bioData.setRoles(Collections.singletonList(Role.SUPER_ADMIN));
        Admin admin = new Admin();
        admin.setBioData(bioData);
        System.out.println(adminRepository.save(admin));
        System.out.println(adminRepository.findAll());
    }
}