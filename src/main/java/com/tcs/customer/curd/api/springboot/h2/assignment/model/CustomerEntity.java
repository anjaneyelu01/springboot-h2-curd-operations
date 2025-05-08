package com.tcs.customer.curd.api.springboot.h2.assignment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is entity class which will communicate to database
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@Entity
@Data
@Table(name = "CUSTOMERS")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "CUSTOMER_ID")
    private String id;
    @Column(name = "CUSTOMER_NAME")
    private String name;

    @Column(name = "CUSTOMER_EMAIL")
    private String email;
    @Column(name = "CUSTOMER_ANNUAL_SPEND")
    private BigDecimal annualSpend;

    @Column(name = "CUSTOMER_LAST_PURCHASE_DATE")
    private String lastPurchaseDate;

    @PrePersist
    public void prePersist() {

        if (annualSpend == null) {
            annualSpend = new BigDecimal("0");
        }
        if (lastPurchaseDate == null) {
            lastPurchaseDate = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        }
    }
}