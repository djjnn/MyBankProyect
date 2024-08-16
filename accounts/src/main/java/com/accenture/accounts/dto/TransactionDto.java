package com.accenture.accounts.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long accountNumber;
    private LocalDateTime createdDate;
    private Float amount;
    private Float balance;


}
