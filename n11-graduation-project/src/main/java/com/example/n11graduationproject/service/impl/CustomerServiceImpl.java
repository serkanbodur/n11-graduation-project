package com.example.n11graduationproject.service.impl;

import com.example.n11graduationproject.converter.CustomerConverter;
import com.example.n11graduationproject.dto.CreateCustomerDTO;
import com.example.n11graduationproject.dto.CustomerDTO;
import com.example.n11graduationproject.entity.Customer;
import com.example.n11graduationproject.exception.CustomerIsNotExistException;
import com.example.n11graduationproject.repository.CustomerRepository;
import com.example.n11graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Override
    public List<CustomerDTO> findAll() {
        var customers = customerRepository.findAll();
        return CustomerConverter.INSTANCE.convertAllCustomersToCustomerDTOs(customers);
    }

    @Override
    public CustomerDTO save(CreateCustomerDTO createCustomerDTO) {
        var customer = CustomerConverter.INSTANCE.convertCreateCustomerDTOToCustomer(createCustomerDTO);
        customerRepository.save(customer);
        return CustomerConverter.INSTANCE.convertCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO, Long id) {
        // var customer = customerRepository.findById(id).orElseThrow(() -> new CustomerIsNotExistException("The customer is not found!"));
        var optionalCustomer = customerRepository.findById(id);
        Customer customer;
        if(!optionalCustomer.isPresent())
        {
            log.error("The customer is not found!");
            throw new CustomerIsNotExistException("The customer is not found!");
        }
        customer = optionalCustomer.get();
        customer.setId(id);
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setIdNumber(customerDTO.getIdNumber());
        customer.setPhone(customerDTO.getPhone());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setGuaranteeFee(customerDTO.getGuaranteeFee());
        customer.setMonthlyIncome(customerDTO.getMonthlyIncome());
        customerRepository.save(customer);
        return CustomerConverter.INSTANCE.convertCustomerToCustomerDTO(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.findById(id).orElseThrow(() -> {
            log.error("The customer is not found!");
            return new CustomerIsNotExistException("The customer is not found!");
        });
        customerRepository.deleteById(id);
    }
}
