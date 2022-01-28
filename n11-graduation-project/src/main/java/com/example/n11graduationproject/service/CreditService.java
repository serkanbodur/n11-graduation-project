package com.example.n11graduationproject.service;

import com.example.n11graduationproject.dto.ResponseCreditDTO;

import java.util.Date;

public interface CreditService {

    ResponseCreditDTO applyCredit(String idNumber);
    ResponseCreditDTO findCreditApply(String idNumber, Date dateOfBirth);
    Double calculateCreditScore(Double monthlyIncome, Double guaranteeFee, String lastDigitOfID);
}
