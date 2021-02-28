package com.romantulchak.virtualuniversity.service.impl;

import com.romantulchak.virtualuniversity.dto.RoleDTO;
import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.repository.RoleRepository;
import com.romantulchak.virtualuniversity.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository= roleRepository;
    }

    @Override
    public Collection<RoleDTO> getRoles() {
        return roleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RoleDTO convertToDTO(Role role){
        return new RoleDTO(role);
    }
}
