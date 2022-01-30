package com.example.n11graduationproject.entity;

import com.example.n11graduationproject.enums.EnumCreditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credit implements Serializable {

    @SequenceGenerator(name = "generator", sequenceName = "credit_id_seq", allocationSize = 1)
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "credit_score")
    private Double creditScore;

    @Column(name = "credit_status")
    private EnumCreditStatus creditStatus;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
