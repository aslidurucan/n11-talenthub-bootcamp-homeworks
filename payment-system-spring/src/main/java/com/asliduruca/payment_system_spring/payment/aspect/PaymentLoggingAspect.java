package com.asliduruca.payment_system_spring.payment.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PaymentLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(PaymentLoggingAspect.class);

    @Around("execution(* com.asliduruca.payment_system_spring.payment.service.PaymentServiceImpl.processPayment(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("------------------------------------------------");
        log.info("[LOG] Ödeme işlemi başlıyor...");
        log.info("[LOG] Metod: {}", joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        log.info("[LOG] Ödeme işlemi tamamlandı.");
        log.info("[LOG] Süre: {} ms", duration);
        log.info("------------------------------------------------");

        return result;
    }
}