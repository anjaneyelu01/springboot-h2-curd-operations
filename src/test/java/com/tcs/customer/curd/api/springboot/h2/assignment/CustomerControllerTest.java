package com.tcs.customer.curd.api.springboot.h2.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testing create rest endpoint by setting name and email
     *
     * @throws Exception
     */
    @Test
    public void createCustomerTest() throws Exception {
        CustomerEntity customer = new CustomerEntity();
        customer.setName("John");
        customer.setEmail("test@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"));
    }

    /**
     * By providing id, we are trying to get the customer details
     *
     * @throws Exception
     */
    @Test
    public void getCustomerByIdTest() throws Exception {
        // Assuming there is a customer with ID 4
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));
    }

    /***
     * By providing the name as request param, we are trying to get the customer details
     * @throws Exception
     */
    @Test
    public void getCustomerByName() throws Exception {
        // Assuming there is a customer with ID 4
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));
    }

    /**
     * By providing the email as request parameter, we are getting the customer details from the database
     *
     * @throws Exception
     */
    @Test
    public void getCustomerByEmail() throws Exception {
        // Assuming there is a customer with ID 4
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/email"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));
    }

    /**
     * This test case is to update requestbody which we already inserted to database
     *
     * @throws Exception
     */
    @Test
    public void updateCustomerTest() throws Exception {
        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setName("Vikky");
        updatedCustomer.setEmail("test10@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vikky"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test10@gmail.com"));
    }

    /**
     * This test case is used to delete the specific customer record from database
     *
     * @throws Exception
     */
    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/4"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}