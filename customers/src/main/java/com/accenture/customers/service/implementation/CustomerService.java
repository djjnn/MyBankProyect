package com.accenture.customers.service.implementation;

import com.accenture.customers.dto.AccountDto;
import com.accenture.customers.dto.CustomerDto;
import com.accenture.customers.dto.CustomerWithAccounts;
import com.accenture.customers.entity.Customer;
import com.accenture.customers.exception.ResourceAlreadyExists;
import com.accenture.customers.exception.ResourceNotFound;
import com.accenture.customers.mapper.CustomerMapper;
import com.accenture.customers.repository.CustomerRepository;
import com.accenture.customers.service.ICustomerService;
import com.accenture.customers.service.client.AccountFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService{

    private CustomerRepository customerRepository;
    private AccountFeignClient accountFeignClient;

    @Override
    public void createCustomer(CustomerDto customerDto) {

        Optional<Customer> optionalCustomer= customerRepository.findByDocument(customerDto.getDocument());

        if (optionalCustomer.isPresent()){
            throw new ResourceAlreadyExists("Cliente", "Documento", customerDto.getDocument());
        }
        Customer customer = CustomerMapper.mapDtoToCustomer(customerDto, new Customer());
 //       customer.setCreatedDate(LocalDateTime.now());
 //       customer.setCreatedBy("Admin");
        customerRepository.save(customer);
    }

    @Override
    public CustomerDto fetchCustomerByDocument(String document) {

        Customer customer= customerRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFound("Cliente", "Documento", document));
        return CustomerMapper.mapCustomerToDto(customer, new CustomerDto());
    }

    @Override
    public CustomerDto fetchCustomerByEmail(String email) {
        Customer customer= customerRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("Cliente", "Correo Electronico", email));
        return CustomerMapper.mapCustomerToDto(customer, new CustomerDto());
    }


    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customer= customerRepository.findByDocument(customerDto.getDocument()).orElseThrow(
                () -> new ResourceNotFound("Cliente", "Documento", customerDto.getDocument()));

        CustomerMapper.mapDtoToCustomer(customerDto, customer);
        customerRepository.save(customer);

        return customerDto;
    }

    @Override
    public void deleteByDocument(String document) {
        customerRepository.deleteByDocument(document);
    }

    @Override
    public void deleteByEmail(String email) {
        customerRepository.deleteByEmail(email);
    }

    @Override
    public CustomerWithAccounts fetchCustomerWithAccountsByDocument(String document) {
        Customer customer = customerRepository.findByDocument(document).orElseThrow(
                () -> new ResourceNotFound("Cliente", "Documento", document)
        );
        CustomerWithAccounts customerWithAccounts= CustomerMapper.mapCustomerDtoToWithAccounts(customer, new CustomerWithAccounts());
        ResponseEntity<List<AccountDto>> accountResponse = accountFeignClient.fetchCustomerAccounts(customer.getCustomerId());
        List<AccountDto> accounts= accountResponse.getBody();

        customerWithAccounts.setAccounts(accounts);

        return customerWithAccounts;
    }

    @Override
    public List<CustomerDto> fetchAccountCustomers(Long customerId) {
        List<CustomerDto> customerDtos= new ArrayList<>();

        List<Customer> customerList = customerRepository.findAllByCustomerIdOrderByDocument(customerId);

        for (Customer customer : customerList){
            customerDtos.add(CustomerMapper.mapCustomerToDto(customer,new CustomerDto()));
        }

        return customerDtos;
    }
}
