package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
