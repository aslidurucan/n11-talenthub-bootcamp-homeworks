package com.asliduruca.payment_system_spring.payment;

import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "Google Pay", order = 4)
public class GooglePayPayment implements IPayment {
    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with Google Pay.");
        return true;
    }
    @Override
    public String getPaymentName() { return "Google Pay"; }
}