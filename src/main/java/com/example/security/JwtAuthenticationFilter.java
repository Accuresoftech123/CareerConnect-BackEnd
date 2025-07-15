package com.example.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	  @Autowired
	    private CustomUserDetailsService customUserDetailsService;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {

	        String path = request.getServletPath();

	        // ✅ Skip JWT for public endpoints
	        if (
	        	    path.equals("/jobseekers/register") ||
	        	    path.equals("/jobseekers/login") ||
	        	    path.equals("/recruiters/register") ||
	        	    path.equals("/recruiters/login") ||
	        	    request.getMethod().equalsIgnoreCase("OPTIONS")
	        	) {
	        	    chain.doFilter(request, response);
	        	    return;
	        	}

	        String authHeader = request.getHeader("Authorization");
	        String token = null;
	        String username = null;

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	            try {
	                username = jwtUtil.extractUsername(token);
	            } catch (Exception e) {
	                System.out.println("Invalid JWT: " + e.getMessage());
	            }
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

	            if (jwtUtil.validateToken(token, userDetails)) {
	            	
	            	// ✅ Extract role from token and format correctly
	                String rawRole = jwtUtil.extractRole(token); // e.g., "RECRUITER"
	                String authority = "ROLE_" + rawRole;        // Spring expects ROLE_RECRUITER

	                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));

	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        // 🔍 Debug (you can remove in production)
	        System.out.println("Authorization Header: " + authHeader);
	        System.out.println("Token: " + token);
	        System.out.println("Username: " + username);
	        System.out.println("SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());

	        chain.doFilter(request, response);
	    }

}
