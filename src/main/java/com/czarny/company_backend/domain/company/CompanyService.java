package com.czarny.company_backend.domain.company;

import static java.lang.String.format;

import com.czarny.company_backend.domain.common.ItemNotFoundException;
import com.czarny.company_backend.domain.company.model.Company;
import com.czarny.company_backend.domain.company.model.Department;
import com.czarny.company_backend.domain.company.model.Project;
import com.czarny.company_backend.domain.company.model.Team;
import com.czarny.company_backend.domain.company.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

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
        checkCompanyExists(id);

        companyRepository.deleteById(id);
    }

    @Transactional
    public Company updateCompany(Long id, Company company) throws ItemNotFoundException {
        checkCompanyExists(id);
        company.setId(id);
        return companyRepository.save(company);
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
        checkCompanyExists(companyId);

        Department department = departmentRepository.findByIdAndCompanyId(departmentId, companyId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Department with id: %d for company with id: %d not found", departmentId, companyId)
            )
        );
        departmentRepository.delete(department);
    }

    @Transactional
    public Department updateDepartmentInCompany(
        Long departmentId,
        Long companyId,
        Department department
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);

        Company company = companyRepository.findById(companyId).orElseThrow(
            () -> new ItemNotFoundException(format("Company with id: %d not found", companyId))
        );
        department.setCompany(company);
        department.setId(departmentId);

        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamsFromCompanyDepartment(Long companyId, Long departmentId) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);

        return teamRepository.findAllByDepartment_Id(departmentId);
    }

    @Transactional(readOnly = true)
    public Team getTeamFromCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        Team teamFound = teamRepository.findByDepartment_IdAndId(departmentId, teamId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Team with id %d in Department with id %d not found", teamId, departmentId)
            )
        );

        return teamFound;
    }

    @Transactional
    public Team createTeamForCompanyDepartment(
        Long companyId,
        Long departmentId,
        Team team
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        Department department = departmentRepository.findByIdAndCompanyId(departmentId, companyId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Department with id: %d for company with id: %d not found", departmentId, companyId)
            )
        );
        team.setDepartment(department);
        return teamRepository.save(team);
    }

    @Transactional Team updateTeamInCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId,
        Team team
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        checkTeamExistsInDepartment(departmentId, teamId);

        team.setId(teamId);
        return teamRepository.save(team);
    }

    @Transactional
    public void deleteTeamForCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        Team team = teamRepository.findByDepartment_IdAndId(departmentId, teamId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Team with id: %d for department with id: %d not found", teamId, departmentId)
            )
        );
        teamRepository.delete(team);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsFromTeamFromCompanyDepartment(
            Long companyId,
            Long departmentId,
            Long teamId
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        checkTeamExistsInDepartment(departmentId, teamId);

        return projectRepository.findAllByTeam_Id(teamId);
    }

    @Transactional(readOnly = true)
    public Project getProjectFromTeamFromCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId,
        Long projectId
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        checkTeamExistsInDepartment(departmentId, teamId);
        return projectRepository.findByIdAndTeam_Id(projectId, teamId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Project with id %d for Team with id %d not found", projectId, teamId)
            )
        );
    }

    @Transactional
    public Project createProjectForTeamFromCompanyDepartment(
            Long companyId,
            Long departmentId,
            Long teamId,
            Project project
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        Team team = teamRepository.findByDepartment_IdAndId(departmentId, teamId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Team with id: %d from Department with id: %d not found", teamId, departmentId)
            )
        );
        project.setTeam(team);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProjectForTeamFromCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId,
        Long projectId,
        Project project
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        checkTeamExistsInDepartment(departmentId, teamId);
        checkProjectExistsInTeam(teamId, projectId);

        project.setId(projectId);
        return projectRepository.save(project);
    }

    @Transactional
    public void removeProjectFromTeamFromCompanyDepartment(
        Long companyId,
        Long departmentId,
        Long teamId,
        Long projectId
    ) throws ItemNotFoundException {
        checkCompanyExists(companyId);
        checkDepartmentExistsInCompany(companyId, departmentId);
        checkTeamExistsInDepartment(departmentId, teamId);
        Project project = projectRepository.findByIdAndTeam_Id (projectId, teamId).orElseThrow(
            () -> new ItemNotFoundException(
                format("Project with id: %d from Team with id: %d not found", projectId, teamId)
            )
        );

        projectRepository.delete(project);
    }

    private void checkCompanyExists(Long companyId) throws ItemNotFoundException {
        if (!companyRepository.existsById(companyId)) {
            throw new ItemNotFoundException(format("Company with id: %d not found", companyId));
        }
    }

    private void checkDepartmentExistsInCompany(Long companyId, Long departmentId) throws ItemNotFoundException {
        if (!departmentRepository.existsByCompany_IdAndId(companyId, departmentId)) {
            throw new ItemNotFoundException(
                format("Department with id: %d for company with id: %d not found", departmentId, companyId)
            );
        }
    }

    private void checkTeamExistsInDepartment(
        Long departmentId,
        Long teamId
    ) throws ItemNotFoundException {
        if (!teamRepository.existsByDepartment_IdAndId(departmentId, teamId)) {
            throw new ItemNotFoundException(
                format("Team with id: %d for Department with id: %d not found", teamId, departmentId)
            );
        }
    }

    private void checkProjectExistsInTeam(
        Long teamId,
        Long projectId
    ) throws ItemNotFoundException {
        if (!projectRepository.existsByIdAndTeam_id(projectId, teamId)) {
            throw new ItemNotFoundException(
                format("Project with id: %d for Team with id: %d not found", projectId, teamId)
            );
        }
    }
}
