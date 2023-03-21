package kr.co.popool.gateway.config;

import kr.co.popool.gateway.service.JwtCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtCustomService jwtCustomService;

    private static final String[] AUTH_ARR = {
            "/swagger/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "favicon.ico"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(AUTH_ARR);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  //기본 설정 disable 처리
                .csrf().disable()       //csrf 보안 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션 사용 X
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll();

        http
                .logout()
                .logoutSuccessUrl("/");
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http.csrf()
                .disable()
                .build();
    }
}
