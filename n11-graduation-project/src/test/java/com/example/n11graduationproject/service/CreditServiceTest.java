package com.example.n11graduationproject.service;

import com.example.n11graduationproject.converter.CreditConverter;
import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.entity.Customer;
import com.example.n11graduationproject.enums.EnumCreditStatus;
import com.example.n11graduationproject.exception.CreditApplyIsNotExistException;
import com.example.n11graduationproject.exception.CreditApplyIsAlreadyExistException;
import com.example.n11graduationproject.exception.CustomerIsNotExistException;
import com.example.n11graduationproject.repository.CreditRepository;
import com.example.n11graduationproject.repository.CustomerRepository;
import com.example.n11graduationproject.service.impl.CreditServiceImpl;
import com.example.n11graduationproject.service.impl.CustomerServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditServiceTest {

    @InjectMocks
    CreditServiceImpl creditService;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static Double low = 500.0;
    private static Double high = 1000.0;

    @Test
    public void shouldNotValidateCustomerWhenApplyCredit() {

        String idNumber = "123456";
        Exception exception = assertThrows(CustomerIsNotExistException.class, () -> {
            creditService.applyCredit(idNumber);
        });

        String message = exception.getMessage();

        assertTrue(message.contains("The customer with " + idNumber + " identity number is not found"));
    }

    @Test
    public void shouldValidateCustomerApplyIsAlreadyExistWhenApplyCredit() {

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 10.0, Date.valueOf("2022-01-01"), 10.0);

        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);

        when(creditService.applyCredit(customer.getIdNumber())).thenReturn(ResponseCreditDTO.builder().build());

        when(creditRepository.findByIdNumberAndDateOfBirth(customer.getIdNumber(),customer.getDateOfBirth())).thenThrow(new CreditApplyIsAlreadyExistException("The customer with " + customer.getIdNumber() + " identity number has already applied to credit!"));
        assertThrows(CreditApplyIsAlreadyExistException.class, () -> creditService.applyCredit(customer.getIdNumber()));
    }

    @Test
    public void shouldValidateWhenCreditScoreLowerThanFiveHundred(){

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 10.0, Date.valueOf("2022-01-01"), 10.0);

        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertTrue(creditScore<low);
        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.REJECT);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(), 0.0);

    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeLowerThanFiveThousandAndGuaranteeFeeIsNotZero(){

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 4000.0, Date.valueOf("2022-01-01"),3000.0);



        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),10000+customer.getGuaranteeFee()/10);
    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeLowerThanFiveThousandAndGuaranteeFeeIsZero(){

        var customer = new Customer(1L, "666", "firstName", "firstSurname", "11111", 4000.0, Date.valueOf("2022-01-01"), 0.0);


        when(customerRepository.findByIdNumber(String.valueOf(666L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),10000);
    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeBetweenFiveThousandAndTenThousandAndGuaranteeFeeIsNotZero(){

        var customer = new Customer(1L, "444", "firstName", "firstSurname", "11111", 6000.0, Date.valueOf("2022-01-01"), 1000.0);


        when(customerRepository.findByIdNumber(String.valueOf(444L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));


        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),20000+customer.getGuaranteeFee()/5);

    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeBetweenFiveThousandAndTenThousandAndGuaranteeFeeIsZero(){

        var customer = new Customer(1L, "666", "firstName", "firstSurname", "11111", 6000.0, Date.valueOf("2022-01-01"), 0.0);


        when(customerRepository.findByIdNumber(String.valueOf(666L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),20000);
    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeGreaterThanTenThousandAndGuaranteeFeeIsNotZero(){

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 15000.0, Date.valueOf("2022-01-01"), 6000.0);


        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),customer.getMonthlyIncome()*2+(customer.getGuaranteeFee()/4));
    }

    @Test
    public void shouldValidateWhenCreditScoreBetweenFiveHundredAndOneThousandAndMonthlyIncomeGreaterThanTenThousandAndGuaranteeFeeIsZero(){

        var customer = new Customer(1L, "444", "firstName", "firstSurname", "11111", 30000.0, Date.valueOf("2022-01-01"), 0.0);


        when(customerRepository.findByIdNumber(String.valueOf(444L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, allOf(greaterThan(low),lessThan(high)));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),customer.getMonthlyIncome()*2);
    }

    @Test
    public void shouldValidateWhenCreditScoreGreaterThanOneThousandAndGuaranteeFeeIsNotZero(){

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 30000.0, Date.valueOf("2022-01-01"), 10000.0);


        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, greaterThanOrEqualTo(high));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),customer.getMonthlyIncome()*4+customer.getGuaranteeFee()/2);
    }

    @Test
    public void shouldValidateWhenCreditScoreGreaterThanOneThousandAndGuaranteeFeeIsZero(){

        var customer = new Customer(1L, "888", "firstName", "firstSurname", "11111", 50000.0, Date.valueOf("2022-01-01"), 0.0);


        when(customerRepository.findByIdNumber(String.valueOf(888L))).thenReturn(customer);
        var creditScore = creditService.calculateCreditScore(customer.getMonthlyIncome(),customer.getGuaranteeFee(),customer.getIdNumber());

        assertThat(creditScore, greaterThanOrEqualTo(high));

        var creditDTO = creditService.applyCredit(customer.getIdNumber());

        assertEquals(creditDTO.getCreditStatus(), EnumCreditStatus.APPROVED);
        assertEquals(creditDTO.getCustomerId(),1L);
        assertEquals(creditDTO.getCreditLimit(),customer.getMonthlyIncome()*4);
    }

    @Test
    public void shouldNotValidateCreditWhenFindCreditApply() {

        var customer = new Customer(1L, "888", "firstName", "firstSurname", "11111", 50000.0, Date.valueOf("2022-01-01"), 0.0);

        String idNumber = "123456";

        Exception exception = assertThrows(CreditApplyIsNotExistException.class, () -> {
            creditService.findCreditApply(idNumber,customer.getDateOfBirth());
        });

        String message = exception.getMessage();

        assertTrue(message.contains("The credit with " + idNumber + " customer identity number and " + customer.getDateOfBirth() + " customer date of birth is not found"));
    }


    @Test
    public void shouldValidateCreditWhenFindCreditApply() {

        var customer = new Customer(1L, "222", "firstName", "firstSurname", "11111", 4000.0, Date.valueOf("2022-01-01"), 3000.0);

        when(customerRepository.findByIdNumber(String.valueOf(222L))).thenReturn(customer);

        var creditDTO = creditService.applyCredit(customer.getIdNumber());
        var credit = CreditConverter.INSTANCE.convertResponseCreditDTOToCredit(creditDTO);

        when(creditRepository.findByIdNumberAndDateOfBirth(customer.getIdNumber(),customer.getDateOfBirth())).thenReturn(credit);

        var findCreditDTO = creditService.findCreditApply(customer.getIdNumber(),customer.getDateOfBirth());

        assertEquals(findCreditDTO.getCreditStatus(), credit.getCreditStatus());
        assertEquals(findCreditDTO.getCustomerId(),customer.getId());
        assertEquals(findCreditDTO.getCreditLimit(),credit.getCreditLimit());

    }
}
