package kr.co.popool.gateway.filters;

import kr.co.popool.gateway.service.JwtCustomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final JwtCustomService jwtCustomService;

    @Autowired
    public AuthorizationFilter(JwtCustomService jwtCustomService) {
        super(Config.class);
        this.jwtCustomService = jwtCustomService;
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            jwtCustomService.isRequestHeaders(request);
            jwtCustomService.isJwtValid(jwtCustomService.resolveToken(request));

            return chain.filter(exchange);
        };
    }
}
