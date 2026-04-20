package com.asliduruca.payment_system_spring.payment.repository;

import com.asliduruca.payment_system_spring.payment.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}