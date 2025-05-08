package com.tcs.customer.curd.api.springboot.h2.assignment.service;

import com.tcs.customer.curd.api.springboot.h2.assignment.dto.CustomerRequest;
import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * This is interface responsible to maintain all the methods related to business logic
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
public interface CustomerService {
    public CustomerEntity createCustomer(CustomerRequest customer);

    public Optional<CustomerEntity> getCustomerById(String id);

    public Optional<List<CustomerEntity>> getCustomerByName(String name);

    public Optional<List<CustomerEntity>> getCustomerByEmail(String email);

    public CustomerEntity updateCustomer(String id, CustomerRequest updateCustomer);

    public void deleteCustomer(String id);

}
