package com.asliduruca.payment_system_spring.payment;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class PaymentProcessor {
    private IPayment payment;

    public void setPayment(IPayment payment) {
        this.payment = payment;
    }

    public void processPayment(Order order) {
        this.payment.pay(order);
    }
}