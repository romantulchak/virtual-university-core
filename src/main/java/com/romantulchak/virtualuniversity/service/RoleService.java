package com.romantulchak.virtualuniversity.service;

import com.romantulchak.virtualuniversity.dto.RoleDTO;
import com.romantulchak.virtualuniversity.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface RoleService {
    Collection<RoleDTO> getRoles();
}
