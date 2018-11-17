package com.flowingsun.common.interceptor;

import com.flowingsun.common.annotation.MethodExcuteTimeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExcuteTimeInterceptor {
    private static Logger logger = LoggerFactory.getLogger(MethodExcuteTimeLog.class);

    @Pointcut("@annotation(com.flowingsun.common.annotation.MethodExcuteTimeLog)")
    public void logMethodTimePointcut() {

    }

    @Around("logMethodTimePointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        logger.info("\n---------------------------【方法执行时间统计】--------------------------\nmethod："+pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()+"\nexecute time： " + (System.currentTimeMillis() - startTime) + "(ms)\n");
        return result;
    }
}
