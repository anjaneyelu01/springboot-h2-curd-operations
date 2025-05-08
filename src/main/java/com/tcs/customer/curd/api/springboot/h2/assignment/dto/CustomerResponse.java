package com.tcs.customer.curd.api.springboot.h2.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for sending response back to customer with required info
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private String description;

    private Optional<CustomerEntity> customer;

    private Optional<List<CustomerEntity>> customers;

    private Tier tier;

    public enum Tier {
        SILVER,
        GOLD,
        PLATNUM
    }

}
