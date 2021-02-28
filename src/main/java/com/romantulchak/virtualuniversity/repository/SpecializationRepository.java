package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
