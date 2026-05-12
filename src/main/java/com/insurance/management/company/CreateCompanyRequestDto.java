package com.insurance.management.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCompanyRequestDto {

    @NotBlank
    @Size(max = 150)
    private String companyName;

    @NotBlank
    @Size(max = 50)
    private String companyCode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
