package com.romantulchak.virtualuniversity.contoller;

import com.romantulchak.virtualuniversity.dto.RoleDTO;
import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/roles")
public class RoleController {

    private RoleServiceImpl roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService){
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Collection<RoleDTO> getRoles(){
        return roleService.getRoles();
    }
}
