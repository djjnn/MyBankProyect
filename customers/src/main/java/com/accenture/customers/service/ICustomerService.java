package com.accenture.customers.service;

import com.accenture.customers.dto.CustomerDto;
import com.accenture.customers.dto.CustomerWithAccounts;

import java.util.List;

public interface ICustomerService {

    void createCustomer(CustomerDto customerDto);

    CustomerDto fetchCustomerByDocument(String document);

    CustomerDto fetchCustomerByEmail(String email);

    List<CustomerDto> fetchAccountCustomers(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteByDocument(String document);

    void deleteByEmail(String email);

    CustomerWithAccounts fetchCustomerWithAccountsByDocument(String document);

}
