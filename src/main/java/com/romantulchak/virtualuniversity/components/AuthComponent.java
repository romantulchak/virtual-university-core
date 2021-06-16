package com.romantulchak.virtualuniversity.components;

import com.romantulchak.virtualuniversity.repository.SubjectRepository;
import com.romantulchak.virtualuniversity.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthComponent {

    private final SubjectRepository subjectRepository;

    @Autowired
    public AuthComponent(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }


    public boolean hasPermission(Authentication authentication, long id){
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
            return currentUser.getId() == id;
        }
        return false;
    }

    public boolean hasPermissionToSubject(Authentication authentication, long subjectId){
        if(!authentication.getPrincipal().equals("anonymousUser")) {
            UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
            return subjectRepository.hasAccessToSubject(currentUser.getId(), subjectId);
        }
        return false;
    }
}
