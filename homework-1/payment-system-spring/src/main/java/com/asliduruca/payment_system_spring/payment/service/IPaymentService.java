package com.asliduruca.payment_system_spring.payment.service;

import com.asliduruca.payment_system_spring.payment.dto.PaymentRequestDto;
import com.asliduruca.payment_system_spring.payment.dto.PaymentResponseDto;

import java.util.List;

public interface IPaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto request);
    List<PaymentResponseDto> getAllTransactions();
    List<String> getPaymentMethods();
}