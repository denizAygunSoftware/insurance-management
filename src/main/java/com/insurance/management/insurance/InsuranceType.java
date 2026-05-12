package com.insurance.management.insurance;

import com.insurance.management.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "insurance_types")
public class InsuranceType extends BaseEntity {

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String insuranceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType category;

    public InsuranceType(){

    }
    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }
}
