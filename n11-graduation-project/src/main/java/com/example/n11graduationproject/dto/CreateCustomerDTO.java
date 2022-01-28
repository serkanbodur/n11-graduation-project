package com.example.n11graduationproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateCustomerDTO {
    private String idNumber;
    private String name;
    private String surname;
    private String phone;
    private Double monthlyIncome;
    private Date dateOfBirth;
    private Double guaranteeFee;
}
