package com.example.n11graduationproject.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
//@Table(name = "users")
public class User implements Serializable {

    @SequenceGenerator(name = "generator", sequenceName = "user_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_number", length = 50)
    private String idNumber;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "PHONE", length = 15)
    private String phone;

    @Column(name = "monthly_income", scale=2, precision = 15)
    private BigDecimal monthlyIncome;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(name = "monthly_income", scale=2, precision = 15)
    private BigDecimal guaranteeFee;

}
