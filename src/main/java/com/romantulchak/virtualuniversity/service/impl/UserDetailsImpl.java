package com.romantulchak.virtualuniversity.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romantulchak.virtualuniversity.model.Role;
import com.romantulchak.virtualuniversity.model.Student;
import com.romantulchak.virtualuniversity.model.Teacher;
import com.romantulchak.virtualuniversity.model.UserAbstract;
import com.romantulchak.virtualuniversity.model.enumes.ERole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private long id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String type;

    public UserDetailsImpl(long id, String username, String password, Collection<? extends GrantedAuthority> authorities, String type){
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.type = type;
    }
    public static UserDetailsImpl build(UserAbstract user, Set<Role> roles, ERole type) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities,
                type.name());
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
