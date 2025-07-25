package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
    private JwtAuthenticationFilter jwtFilter;  // ✅ Inject JWT Filter
	
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           
    	 http
         .csrf(csrf -> csrf.disable())
         .cors(Customizer.withDefaults())
         .authorizeHttpRequests(auth -> auth
        		 // === PUBLIC ENDPOINTS ===
        		 .requestMatchers(
        				 "/api/jobseekers/register",
        	                "/api/jobseekers/login",
        	                "/api/jobseekers/verify-otp",
        	                "/api/jobseekers/resend-otp",
        	                "/api/jobseekers/Send-Otp/**",
        	              
        	                "/api/jobseekers/Set-password/**",
        	                "/api/jobseekers/send-mobile-otp",
        	                //"api/jobseekers/recent",
        	                //"api/jobseekers/recent/count",
        	                
        	                "/api/recruiters/register",
        	                "/api/recruiters/login",
        	                "/api/recruiters/verify-otp",
        	                "/api/recruiters/send-otp/**",
        	                "/api/recruiters/Set-password/**",
        	                
        	                "/api/admin/register",
        	                "/api/admin/login",
        	                "/api/admin/jobseekers/count",
        	                "/api/admin/recruiters/count",
        	                "/api/admin/jobseekers/report",
        	                //"/api/admin/jobposts/recent/count",
        	                //"/api/admin/jobseeker/recent",
        	                //"/api/admin/jobseeker/recent/count",
        	                //"/api/admin/recruiter/recent",
        	                //"api/admin/recruiter/recent/count",
        	                  "/api/admin/send-otp/{email}",
        	                "/api/admin/verify-otp/{email}/{otp}",
        	                "/api/admin/Set-password/{adminId}/{newPassword}",

        	                "/api/auth/google-login",
        	                 "api/payments/total-amount",
        	                "/api/jobposts/search",
        	                "/api/jobposts/active",
        	                "/api/jobposts/active/count",
        	                "/api/jobposts/closed",
        	                "/api/jobposts/closed/filtered",
        	                "/api/jobposts/closed/before-date",
        	              //  "/api/jobposts/{id}",
        	                "/api/jobposts/close/count",
        	                
        	                "/api/payments/create-order",
        	                "/api/payments/webhook",
        	                
        	                "/api/jobseekers/{id}/upload-resume",//for testing 
        	                "/api/jobseekers/getprofile/{id}",//for testing 
        	                //"/api/jobseekers/recent", //testing 
        	                //"/api/recruiters/recent",  //for testing 
        	               "/api/recruiters/profile-image/{id}"
        	                ).permitAll()
             // ✅ ✅ ✅ allow preflight OPTIONS requests globally
             .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
             
             // === NEW: Secure this endpoint === for both jobseeker and recruiter
             .requestMatchers("/api/jobposts/{id}").authenticated()
             
          // === JOBSEEKER-ONLY PROTECTED ENDPOINTS ===
             .requestMatchers("/api/jobseekers/**",
                     "/api/jobposts/jobseeker/**",
                     "/api/jobseekers/saved-jobs/**",
                     "/api/payments/jobseeker/**",
                     "/api/payments/confirm-payment",
                     "/api/payments/total-amount",
                     
                     "/api/applications/applyjob/**",
                     "/api/applications/job-seeker/**",             // apply + my applications
                     "/api/applications/jobseeker/**",
                     "/api/applications/{applicationId}"// applied jobs + count
                    
                     
                     ).hasRole("JOBSEEKER")
             
             // === RECRUITER-ONLY PROTECTED ENDPOINTS ===
             .requestMatchers(  "/api/recruiters/**",
                     "/api/recruiters/profile/**",
                     "/api/recruiters/dashboard/**",
                     "/api/jobposts/recruiter/**",
                     
                     "/api/applications/job-post/**",               // recruiter: all applicants for job post
                     "/api/applications/*/status/**",               // recruiter: update status
                     "/api/applications/*/shortlist" ,              // recruiter: shortlist applicant
                     "/api/jobposts/applicants/*/shortlist"
            		 ).hasRole("RECRUITER")
          // ✅ === ADMIN-ONLY PROTECTED ENDPOINTS ===
             .requestMatchers("/api/admin/**").hasRole("ADMIN")
             .anyRequest().authenticated()
         )
         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // ✅ Disable session for JWT
         .exceptionHandling(exception -> exception
                 .authenticationEntryPoint((request, response, authException) -> {
//                     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//                 })
                	 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                     response.setContentType("application/json");
                     response.getWriter().write("{\"message\": \"Unauthorized or expired token\"}");
                 })
                 .accessDeniedHandler((request, response, accessDeniedException) -> {
                     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                     response.setContentType("application/json");
                     response.getWriter().write("{\"message\": \"Access denied\"}");
                 })
                 
        		 )
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // ✅ ADD JWT Filter in chain
         .httpBasic(Customizer.withDefaults())
         .logout(logout -> logout.permitAll());


        return http.build();
    }
	
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authentication provider bean using CustomUserDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
