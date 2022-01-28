package com.example.n11graduationproject.repository;

import com.example.n11graduationproject.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CreditRepository extends JpaRepository <Credit, Long> {

    @Query("select credit from Credit credit join credit.customer customer where customer.idNumber= :idNumber and customer.dateOfBirth= :dateOfBirth")
    Credit findByIdNumberAndDateOfBirth(String idNumber, Date dateOfBirth);
}
