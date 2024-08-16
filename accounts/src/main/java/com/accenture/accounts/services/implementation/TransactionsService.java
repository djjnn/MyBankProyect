package com.accenture.accounts.services.implementation;

import com.accenture.accounts.dto.NewTransactionDto;
import com.accenture.accounts.dto.TransactionDto;
import com.accenture.accounts.entity.Account;
import com.accenture.accounts.entity.Transactions;
import com.accenture.accounts.exception.ResourceNotFound;
import com.accenture.accounts.mapper.TransactionsMapper;
import com.accenture.accounts.repository.AccountRepository;
import com.accenture.accounts.repository.TransactionRepository;
import com.accenture.accounts.services.ITransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class TransactionsService implements ITransactionsService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    @Override
    public void create(NewTransactionDto transactionsDto) {
        Account account = accountRepository.findById(transactionsDto.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFound("Cuenta", "Numero", transactionsDto.getAccountNumber().toString())
        );
        Transactions transactions = TransactionsMapper.mapToNewTransactions(transactionsDto,  new Transactions());
        transactions.setBalance(account.getBalance() + transactions.getAmount());
        account.setBalance(transactions.getBalance());
        accountRepository.save(account);
        transactionRepository.save(transactions);
    }

    @Override
    public List<TransactionDto> fetchAccountTransactions(Long accountNumber) {
        List<TransactionDto> transactionDtos= new ArrayList<>();

        List<Transactions> transactionsList = transactionRepository.findAllByAccountNumberOrderByTransactionId(accountNumber);

        for (Transactions transactions: transactionsList){
            transactionDtos.add(TransactionsMapper.mapToTransactionsDto(transactions,new TransactionDto()));
        }
        return transactionDtos;
    }


}
