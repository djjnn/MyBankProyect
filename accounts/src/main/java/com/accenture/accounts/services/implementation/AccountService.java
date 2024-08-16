package com.accenture.accounts.services.implementation;

import com.accenture.accounts.dto.AccountDto;
import com.accenture.accounts.dto.AccountWithCustomer;
import com.accenture.accounts.dto.CustomerDto;
import com.accenture.accounts.dto.NewAccountDto;
import com.accenture.accounts.entity.Account;
import com.accenture.accounts.exception.ResourceNotFound;
import com.accenture.accounts.mapper.AccountMapper;
import com.accenture.accounts.repository.AccountRepository;
import com.accenture.accounts.services.IAccountService;
import com.accenture.accounts.services.client.CustomersFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;
    private CustomersFeignClient customersFeignClient;

    @Override
    public AccountDto create(NewAccountDto accountDto) {

        Account account = AccountMapper.mapToNewAccount(accountDto,  new Account());
        Long accountNumber = 100000000L + new Random().nextInt(900000000);
        account.setAccountNumber(accountNumber);
        account.setBalance(0.0F);

        Account saveAccount=accountRepository.save(account);

        return AccountMapper.mapToAccountDto(saveAccount, new AccountDto());
    }

    @Override
    public AccountDto fetch(Long accountNumber) {

        Account account = accountRepository.findById(accountNumber).orElseThrow(
                () -> new ResourceNotFound("Cuenta", "Numero de cuenta", accountNumber.toString())
        );

        return AccountMapper.mapToAccountDto(account, new AccountDto());
    }

    @Override
    public List<AccountDto> fetchCustomerAccounts(Long customerId) {

        List<AccountDto> accountDtos= new ArrayList<>();

        List<Account> accountList = accountRepository.findAllByCustomerIdOrderByAccountNumber(customerId);

        for (Account account : accountList){
            accountDtos.add(AccountMapper.mapToAccountDto(account,new AccountDto()));
        }

        return accountDtos;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        return null;
    }

    @Override
    public AccountWithCustomer fetchAccountsWithCustomer(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFound("Cuenta", "Numero de cuenta", accountNumber.toString())
        );
        AccountWithCustomer accountWithCustomer=AccountMapper.mapAccountDtoToWithCustomer(account,new AccountWithCustomer());
        ResponseEntity<List<CustomerDto>> customerResponse= customersFeignClient.fetchAccountCustomers(accountWithCustomer.getCustomerId());
        List<CustomerDto> customer= customerResponse.getBody();

        accountWithCustomer.setCustomerDto(customer.get(0));

        return accountWithCustomer;

    }
}
