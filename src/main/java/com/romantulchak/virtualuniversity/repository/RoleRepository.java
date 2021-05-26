package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.enumes.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType role);

}
