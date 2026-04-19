package com.aslidurucan.homework1.payment.kotu;

public class PayPalPayment {
    public boolean paypalPay(double amount) {
        System.out.println(amount + " USD paid with PayPal.");
        return true;
    }
}