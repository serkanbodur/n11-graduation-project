package com.example.n11graduationproject.repository;

import com.example.n11graduationproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {
    Customer findByIdNumber(String idNumber);
}
