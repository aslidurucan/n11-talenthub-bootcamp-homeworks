package com.asliduruca.payment_system_spring.payment;

import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "N11Pay", order = 4)
public class N11PayPayment implements IPayment {

    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with N11Pay.");
        return true;
    }

    @Override
    public String getPaymentName() {
        return "N11Pay";
    }
}