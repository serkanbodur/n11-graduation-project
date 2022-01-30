package com.example.n11graduationproject.service.impl;

import com.example.n11graduationproject.converter.CreditConverter;
import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.entity.Customer;
import com.example.n11graduationproject.enums.EnumCreditStatus;
import com.example.n11graduationproject.exception.CreditApplyIsNotExistException;
import com.example.n11graduationproject.exception.CreditApplyIsAlreadyExistException;
import com.example.n11graduationproject.exception.CustomerIsNotExistException;
import com.example.n11graduationproject.repository.CreditRepository;
import com.example.n11graduationproject.repository.CustomerRepository;
import com.example.n11graduationproject.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private static final double creditLimitMultiplier = 4;

    @Override
    public ResponseCreditDTO applyCredit(String idNumber) {

        Customer customer = customerRepository.findByIdNumber(idNumber);

        if(customer == null)
        {
            log.error("The customer is not found!");
            throw new CustomerIsNotExistException("The customer with " + idNumber + " identity number is not found");
        }

        if(creditRepository.findByIdNumberAndDateOfBirth(customer.getIdNumber(),customer.getDateOfBirth()) != null)
        {
            log.error("The customer has already applied to credit!");
            throw new CreditApplyIsAlreadyExistException("The customer with " + idNumber + " identity number has already applied to credit!");
        }

        var resultCreditDTO = ResponseCreditDTO.builder().build();
        var creditScore = calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(), customer.getIdNumber());

        resultCreditDTO.setCreditScore(creditScore);
        resultCreditDTO.setCustomerId(customer.getId());
        resultCreditDTO.setCreditStatus(EnumCreditStatus.APPROVED);

        Double creditLimit = 0.0;
        boolean haveGuaranteeFee= customer.getGuaranteeFee() > 0;

        if (creditScore < 500) {
            resultCreditDTO.setCreditStatus(EnumCreditStatus.REJECT);
        }
        else if (creditScore < 1000) {
            if (customer.getMonthlyIncome() < 5000) {
                creditLimit = 10000.0;
                if(haveGuaranteeFee) creditLimit += customer.getGuaranteeFee() / 10;
            }
            else if (customer.getMonthlyIncome() < 10000) {
                creditLimit = 20000.0;
                if(haveGuaranteeFee) creditLimit += customer.getGuaranteeFee() / 5;
            }
            else {
                creditLimit = customer.getMonthlyIncome() * creditLimitMultiplier / 2;
                if(haveGuaranteeFee) creditLimit += customer.getGuaranteeFee() / 4;
            }
        }
        else{
            creditLimit = customer.getMonthlyIncome() * creditLimitMultiplier;
            if(haveGuaranteeFee) creditLimit += customer.getGuaranteeFee() / 2;
        }

        resultCreditDTO.setCreditLimit(creditLimit);
        var resultCredit = CreditConverter.INSTANCE.convertResponseCreditDTOToCredit(resultCreditDTO);
        creditRepository.save(resultCredit);
        return resultCreditDTO;
    }

    @Override
    public ResponseCreditDTO findCreditApply(String idNumber, Date dateOfBirth)
    {

        var credit = creditRepository.findByIdNumberAndDateOfBirth(idNumber, dateOfBirth);

        if(Objects.isNull(credit))
        {
            log.error("The credit record is not found");
            throw new CreditApplyIsNotExistException("The credit with " + idNumber + " customer identity number and " + dateOfBirth + " customer date of birth is not found");
        }
        log.info("The credit record is found");
        return CreditConverter.INSTANCE.convertCreditToResponseCreditDTO(credit);
    }

    // The credit score is calculated by adding 1/100 of the monthly income, 1/100 of the guarantee fee and the last digit of the TR ID number multiplied by 100.
    @Override
    public Double calculateCreditScore(Double monthlyIncome, Double guaranteeFee, String lastDigitOfID) {
        var lastDigitId =  lastDigitOfID.substring(lastDigitOfID.length()-1);
        var creditScore = (monthlyIncome*0.01+guaranteeFee*0.1)+(Double.valueOf(lastDigitId)*100);
        return creditScore;
    }


}
