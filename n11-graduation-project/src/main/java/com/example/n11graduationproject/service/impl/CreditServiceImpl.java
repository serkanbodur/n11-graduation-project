package com.example.n11graduationproject.service.impl;

import com.example.n11graduationproject.converter.CreditConverter;
import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.entity.Customer;
import com.example.n11graduationproject.enums.EnumCreditStatus;
import com.example.n11graduationproject.exception.CreditApplyIsNotExistException;
import com.example.n11graduationproject.exception.CustomerIsNotExistException;
import com.example.n11graduationproject.repository.CreditRepository;
import com.example.n11graduationproject.repository.CustomerRepository;
import com.example.n11graduationproject.service.CreditService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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

        var resultCreditDTO = ResponseCreditDTO.builder().build();
        var creditScore = calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(), customer.getIdNumber());

        // resultCreditDTO.setCreditScore(creditScore);
        resultCreditDTO.setCustomerId(customer.getId());
        resultCreditDTO.setCreditStatus(EnumCreditStatus.APPROVED);

        if (creditScore < 500) {
            resultCreditDTO.setCreditStatus(EnumCreditStatus.REJECT);
        }
        else if (creditScore < 1000) {
            if (customer.getMonthlyIncome() < 5000) {
                if (customer.getGuaranteeFee() > 0) {
                    resultCreditDTO.setCreditLimit(10000 + customer.getGuaranteeFee() / 10);
                }
                else{
                    resultCreditDTO.setCreditLimit(Double.valueOf(10000));
                }
            }
            else if (customer.getMonthlyIncome() < 10000) {
                if (customer.getGuaranteeFee() > 0) {
                    resultCreditDTO.setCreditLimit(20000 + customer.getGuaranteeFee() / 5);
                }
                else {
                    resultCreditDTO.setCreditLimit(Double.valueOf(20000));
                }

            }
            else {
                if (customer.getGuaranteeFee() > 0) {
                    resultCreditDTO.setCreditLimit((customer.getMonthlyIncome()*creditLimitMultiplier/2)+(customer.getGuaranteeFee()/4));
                }
                else{
                    resultCreditDTO.setCreditLimit(customer.getMonthlyIncome()*creditLimitMultiplier/2);
                }
            }
        }
        else{
            if(customer.getGuaranteeFee() > 0) {
                resultCreditDTO.setCreditLimit((customer.getMonthlyIncome()*creditLimitMultiplier)+(customer.getGuaranteeFee()/2));
            }
            else {
                resultCreditDTO.setCreditLimit(customer.getMonthlyIncome()*creditLimitMultiplier);
            }

        }

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
