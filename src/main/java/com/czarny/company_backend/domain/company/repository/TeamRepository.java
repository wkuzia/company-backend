package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public List<Team> findAllByDepartment_Id(Long departmentId);
    public Optional<Team> findByDepartment_IdAndId(Long departmentId, Long teamId);
    public boolean existsByDepartment_IdAndId(Long departmentId, Long teamId);
}
