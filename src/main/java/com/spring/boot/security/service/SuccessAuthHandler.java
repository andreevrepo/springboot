package com.spring.boot.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Логика выбора url взависимости от роли
 */
@Service
public class SuccessAuthHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        handle(httpServletResponse,authentication);
    }

    private void handle(HttpServletResponse httpServletResponse, Authentication authentication) throws IOException{
        String url = choiceUrl(authentication);
        httpServletResponse.sendRedirect(url);
    }

    private String choiceUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if(authorities.contains(new SimpleGrantedAuthority("ADMIN"))){
            return "/admin";
        }else if(authorities.contains(new SimpleGrantedAuthority("USER"))){
            return "/user";
        }else{
            throw new IllegalStateException();
        }

    }
}
