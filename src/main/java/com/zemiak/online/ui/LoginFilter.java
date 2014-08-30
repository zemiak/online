package com.zemiak.online.ui;

import java.io.IOException;
import java.security.Principal;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
 
public class LoginFilter implements Filter {
 
    @Override
    public void destroy() {
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
 
        Principal principal = ((HttpServletRequest) servletRequest).getUserPrincipal();
 
        System.out.println("Yeeey! Get me here and find me in the database: " + principal.getName());
 
        filterChain.doFilter(servletRequest, servletResponse);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}