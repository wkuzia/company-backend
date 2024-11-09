package com.czarny.company_backend.domain.company;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import com.czarny.company_backend.domain.common.ItemNotFoundException;
import com.czarny.company_backend.domain.company.model.Company;
import com.czarny.company_backend.domain.company.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {CompanyController.API_URL})
public class CompanyController {

    public static final String API_URL = "companies";

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) throws ItemNotFoundException {
        return ok(companyService.getCompanyById(id));
    }

    @PostMapping
    public  ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company newCompany = companyService.createCompany(company);
        return status(HttpStatus.CREATED).body(newCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) throws ItemNotFoundException {
        companyService.deleteCompany(id);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
        @PathVariable Long id,
        @RequestBody Company company
    ) throws ItemNotFoundException
    {
        Company updatedCompany = companyService.updateCompany(id, company);
        return status(HttpStatus.OK).body(updatedCompany);
    }

    @GetMapping("/{companyId}/departments")
    public ResponseEntity<List<Department>> getAllDepartmentsFromCompany(
            @PathVariable Long companyId
    ) throws ItemNotFoundException {
        return ok(companyService.getAllDepartmentsFromCompany(companyId));
    }

    @GetMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Department> getDepartmentFromCompany(
        @PathVariable Long companyId,
        @PathVariable Long departmentId
    ) throws ItemNotFoundException {
        return ok(companyService.getDepartmentFromCompany(departmentId, companyId));
    }

    @PostMapping("/{companyId}/departments")
    public ResponseEntity<Department> createDepartment(
        @PathVariable Long companyId,
        @RequestBody Department department
    ) throws ItemNotFoundException
    {
        Department createdDepartment = companyService.createDepartmentInCompany(department, companyId);
        return status(HttpStatus.CREATED).body(createdDepartment);
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Void> deleteDepartmentFromCompany(
        @PathVariable Long companyId,
        @PathVariable Long departmentId
    ) throws ItemNotFoundException {
        companyService.deleteDepartmentFromCompany(departmentId, companyId);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Department> updateDepartmentInCompany(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @RequestBody Department department
    ) throws ItemNotFoundException {
        Department updatedDepartment = companyService.updateDepartmentInCompany(departmentId, companyId, department);
        return status(HttpStatus.OK).body(updatedDepartment);
    }
}
