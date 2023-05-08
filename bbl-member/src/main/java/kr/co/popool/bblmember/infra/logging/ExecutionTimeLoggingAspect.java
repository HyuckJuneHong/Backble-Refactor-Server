package kr.co.popool.bblmember.infra.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class ExecutionTimeLoggingAspect {

    @Pointcut("execution(* kr.co.popool.bblmember.service.CareerService.*(..))")
    public void CareerService(){
    }

    @Pointcut("execution(* kr.co.popool.bblmember.service.CommonService.*(..))")
    public void CommonService(){
    }

    @Pointcut("execution(* kr.co.popool.bblmember.service.CorporateService.*(..))")
    public void CorporateService(){
    }

    @Pointcut("execution(* kr.co.popool.bblmember.service.GradeService.*(..))")
    public void GradeService(){
    }

    @Pointcut("execution(* kr.co.popool.bblmember.service.MemberService.*(..))")
    public void MemberService(){
    }

    @Pointcut("execution(* kr.co.popool.bblmember.service.ScoreService.*(..))")
    public void ScoreService(){
    }

    @Around("ScoreService() || MemberService() || GradeService() || CommonService() || CorporateService() || CareerService()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{

        String className = joinPoint.getTarget().getClass().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        log.info("[ExecutionTime] {}.{} : {}ms", className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }
}
