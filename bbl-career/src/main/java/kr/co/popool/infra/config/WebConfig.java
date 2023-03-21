package kr.co.popool.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final long MAX_AGE_SECOND = 3600;
	private static final String[] AUTH_ARR = {
		"/swagger/**",
		"/v2/api-docs",
		"/configuration/ui",
		"/swagger-resources/**",
		"/configuration/security",
		"/swagger-ui.html",
		"/swagger-ui/**",
		"/webjars/**",
		"favicon.ico"
	};

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.allowedOriginPatterns("*")
			.maxAge(MAX_AGE_SECOND);
	}
}
