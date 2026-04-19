package com.aslidurucan.homework1.payment.kotu;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CreditCardPayment creditCard = new CreditCardPayment();
        PayPalPayment payPal = new PayPalPayment();
        PaymentProcessor processor = new PaymentProcessor(creditCard, payPal);

        System.out.println("=== Payment System ===");
        System.out.println("Order Total: 250.0 USD");
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. PayPal");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                processor.processPayment(PaymentType.CreditCard, 250.0);
                break;
            case 2:
                processor.processPayment(PaymentType.PayPal, 250.0);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }
}