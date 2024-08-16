package com.accenture.accounts.repository;

import com.accenture.accounts.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    List<Transactions> findAllByAccountNumberOrderByTransactionId(Long accountNumber);
}
