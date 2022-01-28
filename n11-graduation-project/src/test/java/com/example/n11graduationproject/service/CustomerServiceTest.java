package com.example.n11graduationproject.service;

import com.example.n11graduationproject.controller.CustomerController;
import com.example.n11graduationproject.converter.CustomerConverter;
import com.example.n11graduationproject.dto.CreateCustomerDTO;
import com.example.n11graduationproject.dto.CustomerDTO;
import com.example.n11graduationproject.entity.Customer;
import com.example.n11graduationproject.exception.CustomerIsNotExistException;
import com.example.n11graduationproject.repository.CustomerRepository;
import com.example.n11graduationproject.service.impl.CustomerServiceImpl;
import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.C;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest extends TestCase {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldValidateFındAllCustomerTest() {
        List<Customer> customers = new ArrayList<Customer>();

        var customerOne = new Customer(Long.valueOf(1), "111", "firstName", "firstSurname", "11111", Double.valueOf(100), Date.valueOf("2022-01-01"), Double.valueOf(100));
        var customerTwo = new Customer(Long.valueOf(2), "222", "secondName", "secondSurname", "22222", Double.valueOf(200), Date.valueOf("2022-01-01"), Double.valueOf(200));

        customers.add(customerOne);
        customers.add(customerTwo);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOs = customerService.findAll();
        var customerList = CustomerConverter.INSTANCE.convertCustomerDTOsToCustomers(customerDTOs);

        assertEquals(2, customerList.size());

        assertEquals(customers.get(0), customerList.get(0));
        assertEquals(customers.get(1), customerList.get(1));

        verify(customerRepository, times(1)).findAll();

    }

    /*
    // TODO save set id
    @Test
    public void shouldValidateSaveCustomerTest() {

        var customer = new Customer(Long.valueOf(1), "111", "firstName", "firstSurname", "11111", Double.valueOf(1000), Date.valueOf("2022-01-01"), Double.valueOf(10));

        when(customerRepository.save(customer)).thenReturn(customer);

        var createCustomerDTO = CreateCustomerDTO.builder().build();
        var customerDTO = CustomerConverter.INSTANCE.convertCustomerToCustomerDTO(customer);

        var savedCustomerDTO = customerService.save(createCustomerDTO);

        assertEquals(savedCustomerDTO, customerDTO);
    }

     */

    @Test
    public void updateCustomerTest() {

        var customer = new Customer(Long.valueOf(1), "111", "firstName", "firstSurname", "11111", Double.valueOf(1000), Date.valueOf("2022-01-01"), Double.valueOf(10));

        when(customerRepository.save(customer)).thenReturn(any());

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        var updatedCustomer = new Customer(Long.valueOf(1), "222", "secondName", "secondSurname", "22222", Double.valueOf(2000), Date.valueOf("2022-02-01"), Double.valueOf(20));
        var tempUpdatedCustomerDTO = CustomerConverter.INSTANCE.convertCustomerToCustomerDTO(updatedCustomer);

        CustomerDTO customerDTO = CustomerConverter.INSTANCE.convertCustomerToCustomerDTO(updatedCustomer);

        var updatedCustomerDTO = customerService.update(customerDTO, customerDTO.getId());

        assertEquals(tempUpdatedCustomerDTO, updatedCustomerDTO);
    }

    @Test
    public void shouldNotValidateCustomerWhenCustomerIdNotFound() {

        Exception exception = assertThrows(CustomerIsNotExistException.class, () -> {
            customerService.update(any(), 0L);
        });

        String expectedMessage = "The customer is not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void deleteCustomerTest() {

        var customer = new Customer(Long.valueOf(1), "111", "firstName", "firstSurname", "11111", Double.valueOf(1000), Date.valueOf("2022-01-01"), Double.valueOf(10));

        customerRepository.save(customer);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        customerService.deleteById(customer.getId());

        when(customerRepository.findById(customer.getId())).thenReturn(null);

    }

    @Test
    public void shouldNotValidateCustomerWhenCustomerIsNotFound() {

        Exception exception = assertThrows(CustomerIsNotExistException.class, () -> {
            customerService.deleteById(1L);
        });

        String expectedMessage = "The customer is not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
