package com.accenture.accounts.mapper;

import com.accenture.accounts.dto.AccountDto;
import com.accenture.accounts.dto.AccountWithCustomer;
import com.accenture.accounts.dto.NewAccountDto;
import com.accenture.accounts.entity.Account;

public class AccountMapper {

    public static AccountWithCustomer mapAccountDtoToWithCustomer(Account account, AccountWithCustomer accountWithCustomer)
    {
        accountWithCustomer.setCustomerId(account.getCustomerId());
        accountWithCustomer.setAccountNumber(account.getAccountNumber());
        accountWithCustomer.setAccountType(account.getAccountType());
        accountWithCustomer.setBranch(account.getBranch());
        accountWithCustomer.setBalance(account.getBalance());

        return accountWithCustomer;
    }
    
    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        accountDto.setBranch(account.getBranch());
        accountDto.setCustomerId(account.getCustomerId());

        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto,Account account){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBalance(accountDto.getBalance());
        account.setBranch(accountDto.getBranch());
        account.setCustomerId(accountDto.getCustomerId());

        return account;
    }

    public static Account mapToNewAccount(NewAccountDto AccountDto,Account account){
        account.setAccountType(AccountDto.getAccountType());
        account.setBranch(AccountDto.getBranch());
        account.setCustomerId(AccountDto.getCustomerId());

        return account;
    }
}
