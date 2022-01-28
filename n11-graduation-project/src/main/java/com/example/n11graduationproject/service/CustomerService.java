package com.example.n11graduationproject.service;

import com.example.n11graduationproject.dto.CreateCustomerDTO;
import com.example.n11graduationproject.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO save(CreateCustomerDTO createCustomerDTO);
    CustomerDTO update(CustomerDTO customerDTO, Long id);
    void deleteById(Long id);
}
