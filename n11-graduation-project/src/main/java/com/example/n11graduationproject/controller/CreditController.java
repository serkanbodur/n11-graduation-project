package com.example.n11graduationproject.controller;

import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/credits")
public class CreditController {

    private final CreditService creditService;

    @PostMapping()
    public ResponseEntity<ResponseCreditDTO> applyCredit(@RequestParam String userIdNumber) {
        var creditDTO = creditService.applyCredit(userIdNumber);
        log.info("The credit score and credit limit are created");
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<ResponseCreditDTO> getCredit(@RequestParam String userIdNumber, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth) {
        var creditDTO = creditService.findCreditApply(userIdNumber, dateOfBirth);
        log.info("The credit score calculated successfully");
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }
}
