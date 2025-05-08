package com.tcs.customer.curd.api.springboot.h2.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

/**
 * This class is responsible for getting the customer details and perform the validations on each field
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@Data
public class CustomerRequest {

    private String id;
    @NotNull(message = "name is required. it can't be null")
    @NotBlank(message = "name cannot be empty, please provide name")
    private String name;

    @NotNull(message = "email is required. it can't be null")
    @NotBlank(message = "email cannot be empty, please provide email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = " Invalid request. Email not provided or formatted incorrectly")
    private String email;



    private BigDecimal annualSpend;

    private String lastPurchaseDate;


}