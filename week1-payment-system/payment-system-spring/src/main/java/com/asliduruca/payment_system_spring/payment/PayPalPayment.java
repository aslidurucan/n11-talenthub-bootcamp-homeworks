package com.asliduruca.payment_system_spring.payment;

import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "PayPal", order = 2)
public class PayPalPayment implements IPayment {
    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with PayPal.");
        return true;
    }
    @Override
    public String getPaymentName() { return "PayPal"; }
}