package com.example.n11graduationproject.controller;

import com.example.n11graduationproject.dto.ResponseCreditDTO;
import com.example.n11graduationproject.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Credits", description = "Credit Controller")
public class CreditController {

    private final CreditService creditService;

    @Operation(summary = "Create a new credit apply using idNumber")
    @Parameter(required = true, name = "userIdNumber")
    @PostMapping()
    public ResponseEntity<ResponseCreditDTO> applyCredit(@RequestParam String userIdNumber) {
        var creditDTO = creditService.applyCredit(userIdNumber);
        log.info("The credit score and credit limit are created");
        log.info("Dear customer. Your credit apply has been finalized... Sending message");
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get a credit apply using idNumber and birthdate")
    @GetMapping(value = "")
    public ResponseEntity<ResponseCreditDTO> getCredit(@RequestParam @Parameter String userIdNumber, @RequestParam @Parameter @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth) {
        var creditDTO = creditService.findCreditApply(userIdNumber, dateOfBirth);
        log.info("The credit score calculated successfully");
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }
}
