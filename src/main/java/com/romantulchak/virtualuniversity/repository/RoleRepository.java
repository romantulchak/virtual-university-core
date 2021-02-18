package com.romantulchak.virtualuniversity.repository;

import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);

}
