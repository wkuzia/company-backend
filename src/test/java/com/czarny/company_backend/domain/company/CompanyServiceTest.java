package com.czarny.company_backend.domain.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.czarny.company_backend.domain.common.ItemNotFoundException;
import com.czarny.company_backend.domain.company.model.Company;
import com.czarny.company_backend.domain.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
        new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("company_backend")
            .withUsername("company_backend_user")
            .withPassword("company_backend_user_password");

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
    }

    @Test
    void testGetAllCompanies_ShouldReturnTwoCompanies() {
        Company companyToGetOne = Company.builder().name("companyToGet 1").build();
        Company companyToGetTwo = Company.builder().name("companyToGet 2").build();
        companyRepository.save(companyToGetOne);
        companyRepository.save(companyToGetTwo);

        List<Company> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
    }

    @Test
    void testGetCompanyById_ShouldReturnOneCompany() throws ItemNotFoundException {
        Company company = Company.builder().name("companyToGet").build();
        companyRepository.save(company);

        Company result = companyService.getCompanyById(company.getId());

        assertNotNull(result);
        assertEquals("companyToGet", result.getName());
    }

    @Test
    void getCompanyById_ShouldThrowItemNotFoundWhenCompanyNotExists() {
        assertThrows(ItemNotFoundException.class, () -> companyService.getCompanyById(1L));
    }

    @Test
    void createCompany_ShouldCreateCompany() {
        Company newCompany = Company.builder().name("newCompany").build();
        Company savedCompany = companyService.createCompany(newCompany);

        assertNotNull(savedCompany.getId());
        assertEquals("newCompany", savedCompany.getName());
    }

    @Test
    void deleteCompany_ShouldDeleteCompany() throws ItemNotFoundException {
        Company company = Company.builder().name("CompanyToDelete").build();
        companyRepository.save(company);
        Long companyId = company.getId();

        companyService.deleteCompany(companyId);

        assertFalse(companyRepository.findById(companyId).isPresent());
    }

    @Test
    void updateCompany_ShouldUpdateExistingCompany() throws ItemNotFoundException {
        String newCompanyName = "newCompanyName";
        Company existingCompany = Company.builder().name("existingCompany").build();
        Long existingCompanyId =  companyRepository.save(existingCompany).getId();
        Company dataToUpdate = Company.builder().name(newCompanyName).build();

        Company updatedCompany = companyService.updateCompany(existingCompanyId, dataToUpdate);

        assertEquals(newCompanyName, updatedCompany.getName());
    }

    @Test
    void updateCompany_ShouldThrowItemNotFoundWhenCompanyNotExists() {
        final Long nonExistingCompanyId = 999L;
        Company companyNewData = Company.builder().name("companyNewData").build();

        assertThrows(ItemNotFoundException.class, () -> companyService.updateCompany(nonExistingCompanyId, companyNewData));
    }
}
