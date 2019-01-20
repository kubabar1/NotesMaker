package com.od.notesmaker.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter  {//extends GenericFilterBean

    /*private String scanningToken;

    public TokenAuthenticationFilter(String scanningToken) {
        this.scanningToken = scanningToken;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String authTokenHeader = ((HttpServletRequest)request).getHeader("Authorization");
        if (authTokenHeader != null && authTokenHeader.equals(scanningToken)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("SCANNING_USER", scanningToken));
        }
        chain.doFilter( request, response );
    }*/

}
