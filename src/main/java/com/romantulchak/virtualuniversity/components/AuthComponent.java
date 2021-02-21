package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthComponent {
    public boolean hasPermission(Authentication authentication, long id){
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        return currentUser.getId() == id;
    }
}
