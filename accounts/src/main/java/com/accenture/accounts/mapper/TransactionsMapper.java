package com.accenture.accounts.mapper;

import com.accenture.accounts.dto.NewTransactionDto;
import com.accenture.accounts.dto.TransactionDto;
import com.accenture.accounts.entity.Transactions;

public class TransactionsMapper {

    public static TransactionDto mapToTransactionsDto(Transactions transactions, TransactionDto transactionDto){
        transactionDto.setAmount(transactions.getAmount());
        transactionDto.setBalance(transactions.getBalance());
        transactionDto.setAccountNumber(transactions.getAccountNumber());
        transactionDto.setCreatedDate(transactions.getCreatedDate());

        return transactionDto;
    }

    public static Transactions mapToTransactions(TransactionDto transactionDto, Transactions transactions){
        transactions.setAmount(transactionDto.getAmount());
        transactions.setBalance(transactionDto.getBalance());
        transactions.setAccountNumber(transactionDto.getAccountNumber());
        transactions.setCreatedDate(transactionDto.getCreatedDate());

        return transactions;
    }

    public static Transactions mapToNewTransactions(NewTransactionDto transactionDto, Transactions transactions){
        transactions.setAmount(transactionDto.getAmount());
        transactions.setAccountNumber(transactionDto.getAccountNumber());

        return transactions;
    }
}
