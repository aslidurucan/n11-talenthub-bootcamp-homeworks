package com.asliduruca.payment_system_spring.payment;

public interface IPayment {
    boolean pay(Order order);
    String getPaymentName();
}