package com.example.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:3000","http://192.168.0.124:3030","http://localhost:3000", "http://192.168.0.124:3030","http://192.168.0.124:3000", "http://14.96.203.178:3030","http://14.96.203.178:3000")  // your React frontend URL
	                .allowedMethods("*")
	                .allowedHeaders("*");
	                //.allowCredentials(true);
	    }
}
