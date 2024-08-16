package com.accenture.accounts.dto;


import lombok.Data;

@Data
public class NewTransactionDto {
    private Long accountNumber;
    private Float amount;
}
