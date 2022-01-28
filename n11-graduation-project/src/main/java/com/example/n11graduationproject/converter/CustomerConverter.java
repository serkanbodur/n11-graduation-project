package com.example.n11graduationproject.converter;

import com.example.n11graduationproject.dto.CreateCustomerDTO;
import com.example.n11graduationproject.dto.CustomerDTO;
import com.example.n11graduationproject.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerConverter {

    CustomerConverter INSTANCE = Mappers.getMapper(CustomerConverter.class);

    List<CustomerDTO> convertAllCustomersToCustomerDTOs(List<Customer> customer);

    List<Customer> convertCustomerDTOsToCustomers(List<CustomerDTO> customerDTOs);

    CustomerDTO convertCustomerToCustomerDTO(Customer customer);

    Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO);

    CreateCustomerDTO convertCustomerToCreateCustomerDTO(Customer customer);

    Customer convertCreateCustomerDTOToCustomer(CreateCustomerDTO createCustomerDTO);

}
