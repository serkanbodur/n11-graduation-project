package com.example.n11graduationproject.controller;

import com.example.n11graduationproject.dto.CreateCustomerDTO;
import com.example.n11graduationproject.dto.CustomerDTO;
import com.example.n11graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerDTO>> findAll() {
        var customerDTOs = customerService.findAll();
        log.info("All customers were showed");
        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        var responseCustomerDTO = customerService.save(createCustomerDTO);
        log.info("Customer was saved as successfully");
        return new ResponseEntity<>(responseCustomerDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        var updateCustomerDTO = customerService.update(customerDTO, id);
        log.info("Customer was updated as successfully");
        return new ResponseEntity<>(updateCustomerDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomer(@RequestParam Long id) {
        customerService.deleteById(id);
        log.info("The customer deleted successfully");
        return new ResponseEntity<>("The customer  with " + id + " was deleted successfully", HttpStatus.OK);
    }
}
