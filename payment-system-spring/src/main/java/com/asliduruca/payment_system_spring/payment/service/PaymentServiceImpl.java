package com.asliduruca.payment_system_spring.payment.service;

import com.asliduruca.payment_system_spring.payment.IPayment;
import com.asliduruca.payment_system_spring.payment.Order;
import com.asliduruca.payment_system_spring.payment.PaymentProcessor;
import com.asliduruca.payment_system_spring.payment.PaymentStatus;
import com.asliduruca.payment_system_spring.payment.dto.PaymentRequestDto;
import com.asliduruca.payment_system_spring.payment.dto.PaymentResponseDto;
import com.asliduruca.payment_system_spring.payment.entity.PaymentTransaction;
import com.asliduruca.payment_system_spring.payment.mapper.PaymentTransactionMapper;
import com.asliduruca.payment_system_spring.payment.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentTransactionRepository repository;
    private final Map<String, IPayment> paymentMethods;
    private final PaymentTransactionMapper mapper;
    private final PaymentProcessor processor;
    public PaymentServiceImpl(PaymentTransactionRepository repository,
                              Map<String, IPayment> paymentMethods,
                              PaymentTransactionMapper mapper,
                              PaymentProcessor processor) {
        this.repository = repository;
        this.paymentMethods = paymentMethods;
        this.mapper = mapper;
        this.processor = processor;
    }

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto request) {
        IPayment payment = paymentMethods.get(request.getPaymentMethod());
        if (payment == null) {
            throw new IllegalArgumentException("Geçersiz ödeme yöntemi: " + request.getPaymentMethod());
        }

        Order order = new Order(request.getAmount(), request.getCurrency());

        processor.setPayment(payment);
        processor.processPayment(order);

        PaymentTransaction transaction = new PaymentTransaction(
                request.getPaymentMethod(),
                request.getAmount(),
                request.getCurrency(),
                PaymentStatus.SUCCESS.name()
        );
        PaymentTransaction saved = repository.save(transaction);
        return mapper.toResponseDto(saved);
    }

    @Override
    public List<PaymentResponseDto> getAllTransactions() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPaymentMethods() {
        return new ArrayList<>(paymentMethods.keySet());
    }
}