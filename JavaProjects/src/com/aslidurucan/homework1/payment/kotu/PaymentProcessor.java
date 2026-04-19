package com.aslidurucan.homework1.payment.kotu;

public class PaymentProcessor {
    private CreditCardPayment creditCardPayment;
    private PayPalPayment payPalPayment;

    public PaymentProcessor(CreditCardPayment creditCardPayment, PayPalPayment payPalPayment) {
        this.creditCardPayment = creditCardPayment;
        this.payPalPayment = payPalPayment;
    }

    public void processPayment(PaymentType type, double amount) {
        switch (type) {
            case CreditCard:
                creditCardPayment.pay(amount);
                break;
            case PayPal:
                payPalPayment.paypalPay(amount);
                break;
        }
    }
}