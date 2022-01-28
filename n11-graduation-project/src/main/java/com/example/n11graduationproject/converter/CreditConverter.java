package com.example.n11graduationproject.converter;

import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CreditConverter {

    CreditConverter INSTANCE = Mappers.getMapper(CreditConverter.class);

    @Mapping(target = "customerId", source = "customer.id")
    ResponseCreditDTO convertCreditToResponseCreditDTO(Credit credit);

    @Mapping(target = "customer.id", source = "customerId")
    Credit convertResponseCreditDTOToCredit(ResponseCreditDTO creditDTO);
}
