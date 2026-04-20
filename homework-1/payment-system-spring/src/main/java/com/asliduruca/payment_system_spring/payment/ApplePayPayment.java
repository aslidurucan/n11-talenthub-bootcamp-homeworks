package com.asliduruca.payment_system_spring.payment;

import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "Apple Pay", order = 3)
public class ApplePayPayment implements IPayment {
    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with Apple Pay.");
        return true;
    }
    @Override
    public String getPaymentName() { return "Apple Pay"; }
}