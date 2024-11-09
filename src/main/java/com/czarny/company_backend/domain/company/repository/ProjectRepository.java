package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByTeam_Id(Long teamId);
    Optional<Project> findByIdAndTeam_Id(Long projectId, Long teamId);
    boolean existsByIdAndTeam_id(Long projectId, long teamId);
}
