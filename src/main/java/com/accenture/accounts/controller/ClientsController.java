package com.accenture.accounts.controller;

import com.accenture.accounts.dto.AccountWithCustomer;
import com.accenture.accounts.services.IAccountService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path= "/api")
public class ClientsController {

    IAccountService accountService;

    @GetMapping(value="/fetchWithCustomer/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountWithCustomer> fetchWithCustomer(@PathVariable Long accountNumber)
    {
        AccountWithCustomer accountWithCustomer = accountService.fetchAccountsWithCustomer(accountNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountWithCustomer);
    }
}
