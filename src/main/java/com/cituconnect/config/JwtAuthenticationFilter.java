package com.cituconnect.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cituconnect.util.JwtTokenUtil;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            logger.info("[JWT DEBUG] Authorization header: null for " + request.getMethod() + " " + request.getRequestURI());
        } else {
            String shortVal = authHeader.length() > 50 ? authHeader.substring(0, 50) + "..." : authHeader;
            logger.info("[JWT DEBUG] Authorization header: " + shortVal + " for " + request.getMethod() + " " + request.getRequestURI());
        }

        try {
            String token = extractTokenFromRequest(request);
            if (token != null && jwtTokenUtil.validateToken(token)) {
                String email = jwtTokenUtil.getEmailFromToken(token);
                if (email != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("JWT authenticated user: " + email);
                }
            }
        } catch (Exception ex) {
            logger.error("[JWT DEBUG] token validation error for " + request.getRequestURI(), ex);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}