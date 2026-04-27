package com.asliduruca.payment_system_spring.payment.controller;

import com.asliduruca.payment_system_spring.payment.dto.PaymentRequestDto;
import com.asliduruca.payment_system_spring.payment.dto.PaymentResponseDto;
import com.asliduruca.payment_system_spring.payment.service.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDto> pay(@Valid @RequestBody PaymentRequestDto request) {
        PaymentResponseDto response = paymentService.processPayment(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<PaymentResponseDto>> getAllTransactions() {
        return ResponseEntity.ok(paymentService.getAllTransactions());
    }

    @GetMapping("/methods")
    public ResponseEntity<List<String>> getPaymentMethods() {
        return ResponseEntity.ok(paymentService.getPaymentMethods());
    }
}