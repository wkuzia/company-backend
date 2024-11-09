package com.czarny.company_backend.domain.company;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.noContent;

import com.czarny.company_backend.domain.common.ItemNotFoundException;
import com.czarny.company_backend.domain.company.model.Company;
import com.czarny.company_backend.domain.company.model.Department;
import com.czarny.company_backend.domain.company.model.Project;
import com.czarny.company_backend.domain.company.model.Team;
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
        return noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
        @PathVariable Long id,
        @RequestBody Company company
    ) throws ItemNotFoundException
    {
        Company updatedCompany = companyService.updateCompany(id, company);
        return ok().body(updatedCompany);
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
        Department newDepartment = companyService.createDepartmentInCompany(department, companyId);
        return status(HttpStatus.CREATED).body(newDepartment);
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Void> deleteDepartmentFromCompany(
        @PathVariable Long companyId,
        @PathVariable Long departmentId
    ) throws ItemNotFoundException {
        companyService.deleteDepartmentFromCompany(departmentId, companyId);
        return noContent().build();
    }

    @PutMapping("/{companyId}/departments/{departmentId}")
    public ResponseEntity<Department> updateDepartmentInCompany(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @RequestBody Department department
    ) throws ItemNotFoundException {
        Department updatedDepartment = companyService.updateDepartmentInCompany(departmentId, companyId, department);
        return ok(updatedDepartment);
    }

    @GetMapping("/{companyId}/departments/{departmentId}/teams")
    public ResponseEntity<List<Team>> getAllTeamsFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId
    ) throws ItemNotFoundException {
        return ok(companyService.getTeamsFromCompanyDepartment(companyId, departmentId));
    }

    @GetMapping("/{companyId}/departments/{departmentId}/teams/{teamId}")
    public ResponseEntity<Team> getTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId
    ) throws ItemNotFoundException {
        return ok(companyService.getTeamFromCompanyDepartment(companyId, departmentId, teamId));
    }

    @PostMapping("/{companyId}/departments/{departmentId}/teams")
    public ResponseEntity<Team> createTeamInCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @RequestBody Team team
    ) throws ItemNotFoundException {
        Team newTeam = companyService.createTeamForCompanyDepartment(companyId, departmentId, team);
        return status(HttpStatus.CREATED).body(newTeam);
    }

    @PutMapping("/{companyId}/departments/{departmentId}/teams/{teamId}")
    public ResponseEntity<Team> updateTeamInCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId,
        @RequestBody Team team
    ) throws ItemNotFoundException {
        Team updatedTeam = companyService.updateTeamInCompanyDepartment(companyId, departmentId, teamId, team);
        return ok(updatedTeam);
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}/teams/{teamId}")
    public ResponseEntity<Void> deleteTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId
    ) throws ItemNotFoundException {
        companyService.deleteTeamForCompanyDepartment(companyId, departmentId, teamId);
        return noContent().build();
    }

    @GetMapping("/{companyId}/departments/{departmentId}/teams/{teamId}/projects")
    public ResponseEntity<List<Project>> getProjectsFromTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId
    ) throws ItemNotFoundException {
        return ok(companyService.getProjectsFromTeamFromCompanyDepartment(companyId, departmentId, teamId));
    }

    @GetMapping("/{companyId}/departments/{departmentId}/teams/{teamId}/projects/{projectId}")
    public ResponseEntity<Project> getProjectFromTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId,
        @PathVariable Long projectId
    ) throws ItemNotFoundException {
        return ok(companyService.getProjectFromTeamFromCompanyDepartment(companyId, departmentId, teamId, projectId));
    }

    @PostMapping("/{companyId}/departments/{departmentId}/teams/{teamId}/projects")
    public ResponseEntity<Project> createProjectForTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId,
        @RequestBody Project project
    ) throws ItemNotFoundException {
        Project newProject = companyService.createProjectForTeamFromCompanyDepartment(
            companyId,
            departmentId,
            teamId,
            project
        );
        return status(HttpStatus.CREATED).body(newProject);
    }

    @PutMapping("/{companyId}/departments/{departmentId}/teams/{teamId}/projects/{projectId}")
    public ResponseEntity<Project> updateProjectForTeamFromCompanyDepartment(
        @PathVariable Long companyId,
        @PathVariable Long departmentId,
        @PathVariable Long teamId,
        @PathVariable Long projectId,
        @RequestBody Project project
    ) throws ItemNotFoundException {
        Project updatedProject = companyService.updateProjectForTeamFromCompanyDepartment(
            companyId,
            departmentId,
            teamId,
            projectId,
            project
        );
        return ok(updatedProject);
    }

    @DeleteMapping("/{companyId}/departments/{departmentId}/teams/{teamId}/projects/{projectId}")
    public ResponseEntity<Project> updateProjectForTeamFromCompanyDepartment(
            @PathVariable Long companyId,
            @PathVariable Long departmentId,
            @PathVariable Long teamId,
            @PathVariable Long projectId
    ) throws ItemNotFoundException {
        companyService.removeProjectFromTeamFromCompanyDepartment(companyId, departmentId, teamId, projectId);
        return noContent().build();
    }

}
