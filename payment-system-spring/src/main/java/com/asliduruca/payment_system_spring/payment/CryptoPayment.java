package com.asliduruca.payment_system_spring.payment;

import org.springframework.stereotype.Component;

@Component
@PaymentMethod(name = "Crypto Pay", order = 5)
public class CryptoPayment implements IPayment {
    @Override
    public boolean pay(Order order) {
        System.out.println(order.getOrderSummary() + " paid with Crypto.");
        return true;
    }
    @Override
    public String getPaymentName() { return "Crypto Pay"; }
}
