package com.example.n11graduationproject.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EnumCreditStatus {

    APPROVED("approved"),
    REJECT("reject");

    private String creditStatus;

    @Override
    public String toString() {
        return creditStatus;
    }

}
