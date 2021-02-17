package com.romantulchak.virtualuniversity.security.jwt;

import com.romantulchak.virtualuniversity.model.enumes.ERole;
import com.romantulchak.virtualuniversity.service.impl.StudentDetailsServiceImpl;
import com.romantulchak.virtualuniversity.service.impl.TeacherDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private  JwtUtils jwtUtils;
    private TeacherDetailsServiceImpl teacherDetailsService;

    private StudentDetailsServiceImpl studentDetailsService;

    @Autowired
    public AuthTokenFilter(TeacherDetailsServiceImpl teacherDetailsService, StudentDetailsServiceImpl studentDetailsService, JwtUtils jwtUtils){
        this.teacherDetailsService = teacherDetailsService;
        this.studentDetailsService = studentDetailsService;
        this.jwtUtils = jwtUtils;
    }

    public AuthTokenFilter(){}




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                ERole type = ERole.valueOf(request.getParameter("type"));
                UserDetails userDetails = null;
                switch (type){
                    case STUDENT:
                        userDetails = studentDetailsService.loadUserByUsername(username);
                        break;
                    case TEACHER:
                        userDetails = teacherDetailsService.loadUserByUsername(username);
                        break;
                }



                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
