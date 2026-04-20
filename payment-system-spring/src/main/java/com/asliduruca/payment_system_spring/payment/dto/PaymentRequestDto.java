package com.asliduruca.payment_system_spring.payment.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentRequestDto {
    @NotBlank(message = "Ödeme yöntemi boş olamaz")
    private String paymentMethod;

    @NotNull(message = "Tutar boş olamaz")
    @Positive(message = "Tutar pozitif olmalı")
    private BigDecimal amount;

    @NotBlank(message = "Para birimi boş olamaz")
    @Size(min = 3, max = 3, message = "Para birimi 3 karakter olmalı (örn: USD, TRY)")
    private String currency;

    public PaymentRequestDto() {}

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}