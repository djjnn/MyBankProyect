package com.accenture.accounts.controller;

import com.accenture.accounts.dto.*;
import com.accenture.accounts.services.IAccountService;
import com.accenture.accounts.services.ITransactionsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class AccountController {

    @NonNull
    private IAccountService accountService;

    @NonNull
    private ITransactionsService transactionsService;

    @NonNull
    private Environment environment;

    @NonNull
    private SupportInfoDto supportInfoDto;

    @GetMapping (value = "/support-info")
    public ResponseEntity<SupportInfoDto> supportInfo(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(supportInfoDto);

    }

    @GetMapping (value = "/java-version")
    public ResponseEntity<String> javaVersion(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));

    }

    @PostMapping (value = "/createAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> createAccount(@RequestBody NewAccountDto accountDto){
        AccountDto saveAccount=accountService.create(accountDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveAccount);

    }

    @GetMapping(value="/fetchAccount/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> fetchAccount(@PathVariable Long accountNumber)
    {
        AccountDto accountDto = accountService.fetch(accountNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountDto);
    }

    @GetMapping(value="/fetchCustomerAccounts/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDto>> fetchCustomerAccounts(@PathVariable Long customerId)
    {
        List<AccountDto> accounts= accountService.fetchCustomerAccounts(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accounts);
    }

    @PostMapping (value = "/createTransactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto> createTransactions(@RequestBody NewTransactionDto transactionDto){
        transactionsService.create(transactionDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Transaccion creada exitosamente!!"));

    }

    @GetMapping(value="/fetchTransactions/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDto>> fetchAccountsTransactions(@PathVariable Long accountNumber)
    {
        List<TransactionDto> transactions= transactionsService.fetchAccountTransactions(accountNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactions);
    }




}
