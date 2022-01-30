package com.example.n11graduationproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @SequenceGenerator(name = "generator", sequenceName = "customer_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_number", unique = true, length = 50, nullable = false)
    private String idNumber;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "PHONE", length = 15)
    private String phone;

    @Column(name = "monthly_income")
    private Double monthlyIncome;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "guarantee_fee")
    private Double guaranteeFee;

}
