package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByCompany_Id(Long companyId);
    Optional<Department> findByIdAndCompanyId(Long departmentId, Long companyId);
    boolean existsByCompany_IdAndId(Long companyId, Long id);
}
