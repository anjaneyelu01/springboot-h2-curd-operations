package com.tcs.customer.curd.api.springboot.h2.assignment.service;

import com.tcs.customer.curd.api.springboot.h2.assignment.dto.CustomerRequest;
import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;
import com.tcs.customer.curd.api.springboot.h2.assignment.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

/**
 * this is the main class which holds the all the business logic of the application
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerEntity createCustomer(CustomerRequest customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setAnnualSpend(customer.getAnnualSpend());
        customerEntity.setLastPurchaseDate(customer.getLastPurchaseDate());

        return customerRepository.save(customerEntity);
    }

    @Override
    public Optional<CustomerEntity> getCustomerById(String id) {
        return Optional.ofNullable(customerRepository.findById(id).orElse(null));
    }

    @Override
    public Optional<List<CustomerEntity>> getCustomerByName(String name) {
        return Optional.ofNullable(customerRepository.findByName(name));
    }

    @Override
    public Optional<List<CustomerEntity>> getCustomerByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email));
    }

    @Override
    public CustomerEntity updateCustomer(String id, CustomerRequest updateCustomer) {
        CustomerEntity updatedCustomer = (customerRepository.findById(id).orElse(null));
        if (updatedCustomer != null) {
            updatedCustomer.setName(updateCustomer.getName());
            updatedCustomer.setEmail(updateCustomer.getEmail());
            updatedCustomer.setAnnualSpend(updateCustomer.getAnnualSpend());
            updatedCustomer.setLastPurchaseDate(updateCustomer.getLastPurchaseDate());
            return customerRepository.save(updatedCustomer);
        }
        return null;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
