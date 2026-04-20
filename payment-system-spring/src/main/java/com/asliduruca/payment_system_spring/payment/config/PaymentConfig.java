package com.asliduruca.payment_system_spring.payment.config;

import com.asliduruca.payment_system_spring.payment.IPayment;
import com.asliduruca.payment_system_spring.payment.PaymentMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class PaymentConfig {

    @Bean
    public Map<String, IPayment> paymentMethods(List<IPayment> paymentList) {
        return paymentList.stream()
                .filter(p -> p.getClass().isAnnotationPresent(PaymentMethod.class))
                .collect(Collectors.toMap(
                        p -> p.getClass().getAnnotation(PaymentMethod.class).name(),
                        p -> p
                ));
    }
}