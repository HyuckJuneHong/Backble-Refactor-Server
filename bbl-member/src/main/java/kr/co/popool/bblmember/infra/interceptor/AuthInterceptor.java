package kr.co.popool.bblmember.infra.interceptor;

import kr.co.popool.bblmember.service.JwtCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtCustomService jwtCustomService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler){
        Optional<String> token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)
                .replace("Bearer", "").trim());

        if(!token.isPresent()){
            return true;
        }

        String identity = jwtCustomService.findIdentityByToken(token.get());
        IdentityThreadLocal.set(identity);

        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           ModelAndView modelAndView){

        if(IdentityThreadLocal.get()==null){
            return;
        }

        IdentityThreadLocal.remove();
    }
}
