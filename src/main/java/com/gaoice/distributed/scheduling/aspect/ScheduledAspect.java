package com.gaoice.distributed.scheduling.aspect;

import com.gaoice.distributed.scheduling.DistributedHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoice
 */
@Aspect
public class ScheduledAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledAspect.class);

    private final String appName;

    private final DistributedHandler distributedHandler;

    public ScheduledAspect(String appName, DistributedHandler distributedHandler) {
        this.appName = appName;
        this.distributedHandler = distributedHandler;
    }

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        Date now = new Date();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String cron = getCron(methodSignature);
        if (StringUtils.isEmpty(cron)) {
            LOGGER.warn("[distributed scheduling] only support cron like @Scheduled(cron=...).");
        } else {
            long nextTime = new CronSequenceGenerator(cron).next(now).getTime();
            String key = appName + "-" + getClassMethodName(methodSignature) + "-" + nextTime;
            long timeout = nextTime - now.getTime();
            if (!distributedHandler.lock(key, timeout, TimeUnit.MILLISECONDS)) {
                LOGGER.info("[distributed scheduling][{}] another thread has scheduled, this thread will cancel.", key);
                return;
            }
            LOGGER.info("[distributed scheduling][{}] locked {} milliseconds.", key, timeout);
        }
        point.proceed();
    }

    public String getCron(MethodSignature methodSignature) {
        return methodSignature.getMethod().getAnnotation(Scheduled.class).cron();
    }

    public String getClassMethodName(MethodSignature methodSignature) {
        return methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
    }
}
