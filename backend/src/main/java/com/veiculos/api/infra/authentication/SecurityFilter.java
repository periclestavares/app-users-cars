package com.veiculos.api.infra.authentication;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.veiculos.api.infra.authentication.service.AuthenticationService;
import com.veiculos.api.infra.authentication.service.JWTAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Filter to check the JWTToken on the request
 * @author Pericles TAvares
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTAuthenticationService tokenService;

    @Autowired
    private AuthenticationService service;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = retrieveToken(request);
        if (tokenJWT != null) {
            String login;
            try {
                login = tokenService.verifyToken(tokenJWT);
            } catch (TokenExpiredException e) {
                resolver.resolveException(request, response, null, e);
                return;
            }

            UserDetails user = service.loadUserByUsername(login);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
