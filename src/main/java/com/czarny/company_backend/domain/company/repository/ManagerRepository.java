package com.czarny.company_backend.domain.company.repository;

import com.czarny.company_backend.domain.company.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
