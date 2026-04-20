package com.asliduruca.payment_system_spring.payment.mapper;

import com.asliduruca.payment_system_spring.payment.dto.PaymentResponseDto;
import com.asliduruca.payment_system_spring.payment.entity.PaymentTransaction;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionMapper {

    public PaymentResponseDto toResponseDto(PaymentTransaction transaction) {
        return new PaymentResponseDto(
                transaction.getId(),
                transaction.getPaymentMethod(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }
}