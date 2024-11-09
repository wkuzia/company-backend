package com.czarny.company_backend.domain.company;

import static java.lang.String.format;

import com.czarny.company_backend.domain.common.ItemNotFoundException;
import com.czarny.company_backend.domain.company.model.Company;
import com.czarny.company_backend.domain.company.model.Department;
import com.czarny.company_backend.domain.company.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final ManagerRepository managerRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        return companyRepository.findAll()
            .stream()
            .toList();
    }

    @Transactional(readOnly = true)
    public Company getCompanyById(Long id) throws ItemNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElseThrow(() -> new ItemNotFoundException(format("Company with id: %d not found", id)));
    }

    @Transactional()
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long id) throws ItemNotFoundException {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new ItemNotFoundException(format("Company with id: %d not found", id));
        }
    }

    @Transactional
    public Company updateCompany(Long id, Company company) throws ItemNotFoundException {
        if (companyRepository.existsById(id)) {
            company.setId(id);
            companyRepository.save(company);
            return company;
        } else {
            throw new ItemNotFoundException(format("Company with id: %d not found", id));
        }
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartmentsFromCompany(Long companyId) throws ItemNotFoundException {
        if (companyRepository.existsById(companyId)) {
            return departmentRepository.findAllByCompany_Id(companyId);
        } else {
            throw new ItemNotFoundException(format("Company with id: %d not found", companyId));
        }
    }

    @Transactional(readOnly = true)
    public Department getDepartmentFromCompany(Long departmentId, Long companyId) throws ItemNotFoundException {
        if (companyRepository.existsById(companyId)) {
            return departmentRepository.findByIdAndCompanyId(departmentId, companyId).orElseThrow(
                () -> new ItemNotFoundException(
                    format("Department with id: %d for company with id: %d not found", departmentId, companyId)
                )
            );
        } else {
            throw new ItemNotFoundException(format("Company with id: %d not found", companyId));
        }
    }

    @Transactional
    public Department createDepartmentInCompany(Department department, Long companyId) throws ItemNotFoundException {
        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new ItemNotFoundException(format("Company with id: %d not found", companyId))
        );
        department.setCompany(company);
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartmentFromCompany(Long departmentId, Long companyId) throws ItemNotFoundException {
        if (companyRepository.existsById(companyId)) {
            Department department = departmentRepository.findByIdAndCompanyId(departmentId, companyId).orElseThrow(
                () -> new ItemNotFoundException(
                    format("Department with id: %d for company with id: %d not found", departmentId, companyId)
                )
            );
            departmentRepository.delete(department);
        } else {
            throw new ItemNotFoundException(format("Company with id: %d not found", companyId));
        }
    }

    @Transactional
    public Department updateDepartmentInCompany(
        Long departmentId,
        Long companyId,
        Department department
    ) throws ItemNotFoundException {
        if (departmentRepository.existsByCompany_IdAndId(departmentId, companyId)) {
            Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new ItemNotFoundException(format("Company with id: %d not found", companyId))
            );
            department.setCompany(company);
            department.setId(companyId);
            return departmentRepository.save(department);
        } else {
            throw new ItemNotFoundException(
                format("Department with id: %d for company with id: %d not found", departmentId, companyId)
            );
        }
    }
}
