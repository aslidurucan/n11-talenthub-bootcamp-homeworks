package com.asliduruca.payment_system_spring.payment;

import java.math.BigDecimal;

public class Order {
    private BigDecimal  amount;
    private String currency;

    public Order(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal  getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getOrderSummary() { return amount + " " + currency; }
}