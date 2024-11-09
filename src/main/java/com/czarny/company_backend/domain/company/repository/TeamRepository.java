package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
