package com.example.n11graduationproject.dto;

import com.example.n11graduationproject.enums.EnumCreditStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCreditDTO {
    private Double creditScore;
    private EnumCreditStatus creditStatus;
    private Double creditLimit;
    private Long customerId;
}
