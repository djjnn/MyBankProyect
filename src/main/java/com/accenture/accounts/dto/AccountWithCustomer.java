package com.accenture.accounts.dto;

import lombok.Data;

@Data
public class AccountWithCustomer {
    private Long customerId;
    private Long accountNumber;
    private String accountType;
    private String branch;
    private Float balance;
    private CustomerDto customerDto;
}
