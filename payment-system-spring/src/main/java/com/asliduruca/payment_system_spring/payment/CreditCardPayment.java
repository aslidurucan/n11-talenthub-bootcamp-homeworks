package com.asliduruca.payment_system_spring.payment;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "Credit Card", order = 1)
public class CreditCardPayment implements IPayment {
    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with Credit Card.");
        return true;
    }
    @Override
    public String getPaymentName() { return "Credit Card"; }
}