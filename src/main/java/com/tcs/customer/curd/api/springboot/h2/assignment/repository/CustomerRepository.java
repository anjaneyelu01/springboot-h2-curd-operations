package com.tcs.customer.curd.api.springboot.h2.assignment.repository;

import com.tcs.customer.curd.api.springboot.h2.assignment.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is the repository class whcih communicates to db
 * @Author Anjaneyelu Neerati
 * @Created on 05/07
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    List<CustomerEntity> findByName(String name);
    List<CustomerEntity> findByEmail(String email);
}