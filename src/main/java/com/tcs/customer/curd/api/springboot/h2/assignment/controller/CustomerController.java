package com.tcs.customer.curd.api.springboot.h2.assignment.controller;

import com.tcs.customer.curd.api.springboot.h2.assignment.dto.CustomerRequest;
import com.tcs.customer.curd.api.springboot.h2.assignment.dto.CustomerResponse;
import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;
import com.tcs.customer.curd.api.springboot.h2.assignment.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This controller is responsible for maintaining the different endpoints (curd operations) related to customer
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    public static final String CUSTOMER_RETRIEVED_SUCCESSFULLY = "Customer retrieved successfully";
    public static final String CUSTOMER_S_RETRIEVED_SUCCESSFULLY = "Customer(s) retrieved successfully";
    public static final String CUSTOMER_UPDATED_SUCCESSFULLY = "Customer updated successfully";
    public static final String CUSTOMER_S_NOT_FOUND = "Customer(s) not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_CREATED_SUCCESSFULLY = "Customer created successfully";
    @Autowired
    CustomerService customerService;

    /**
     * This api is responsible for creating the customer details in to database and give the response like "Customer details created successfully"
     * @param customer
     * @return
     */
    @PostMapping()
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customer) {
        try {
            customerService.createCustomer(customer);
            CustomerResponse response = CustomerResponse.builder().description(CUSTOMER_CREATED_SUCCESSFULLY).build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This api is responsible for gettng customer details based on the id (in this case UUID)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse response = null;
        CustomerResponse.Tier tier = null;
        Optional<CustomerEntity> customer = customerService.getCustomerById(id);
        try {
            // Based on the Annual Spending amount we are setting the tiers like Silver, Platinum and Gold to differnt users
            if (customer.isPresent()) {
                int tierVal = customer.stream().findAny().get().getAnnualSpend().intValue();
                if (tierVal < 1000) {
                    tier = CustomerResponse.Tier.SILVER;
                } else if (tierVal >= 1000 && tierVal < 10000) {
                    tier = CustomerResponse.Tier.GOLD;
                } else if (tierVal > 10000) {
                    tier = CustomerResponse.Tier.PLATNUM;
                }
            // Building the response using builder pattern
                response = CustomerResponse.builder().description(CUSTOMER_RETRIEVED_SUCCESSFULLY).tier(tier).customer(customer).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response = CustomerResponse.builder().description(CUSTOMER_NOT_FOUND).build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This api is responsible for getting the customer details based in the name, in this case we have provided name as request arameter
     * @param name
     * @return
     */
    @GetMapping(params = "name")
    public ResponseEntity<CustomerResponse> getCustomerByName(@RequestParam(name = "name") String name) {
        Optional<List<CustomerEntity>> customer = customerService.getCustomerByName(name);
        return searchCustomerByNameOrEmail(customer);
    }

    /**
     * This api is responsible for getting the customer details based in the email, in this case we have provided email as request arameter
     * @param name
     * @return
     */
    @GetMapping(params = "email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam(name = "email") String email) {
        Optional<List<CustomerEntity>> customer = customerService.getCustomerByEmail(email);
        return searchCustomerByNameOrEmail(customer);
    }

    /**
     * this is helper logic to avoid the duplicate code
     * @param customer
     * @return
     */
    private static ResponseEntity<CustomerResponse> searchCustomerByNameOrEmail(Optional<List<CustomerEntity>> customer) {
        CustomerResponse response;
        CustomerResponse.Tier tier = null;
        try {
            if (customer.isPresent()) {
                // Based on the Annual Spending amount we are setting the tiers like Silver, Platinum and Gold to differnt users
                int tierVal = customer.stream().findAny().get().get(0).getAnnualSpend().intValue();
                if (tierVal < 1000) {
                    tier = CustomerResponse.Tier.SILVER;
                } else if (tierVal >= 1000 && tierVal < 10000) {
                    tier = CustomerResponse.Tier.GOLD;
                } else if (tierVal > 10000) {
                    tier = CustomerResponse.Tier.PLATNUM;
                }

                response = CustomerResponse.builder().description(CUSTOMER_S_RETRIEVED_SUCCESSFULLY).customers(customer).tier(tier).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response = CustomerResponse.builder().description(CUSTOMER_S_NOT_FOUND).build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This api is responsible for modifying the customer details based in the id (UUID), in this case we have provided as a path variable
     * @param name
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest updateCustomer) {

        try {
            customerService.updateCustomer(id, updateCustomer);
            CustomerResponse response = CustomerResponse.builder().description(CUSTOMER_UPDATED_SUCCESSFULLY).build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * This api is responsible for deleting the specific customer record based on the ID provided.
     * @param name
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
