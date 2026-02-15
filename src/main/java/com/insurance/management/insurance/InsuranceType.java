package com.insurance.management.insurance;

import com.insurance.management.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class InsuranceType extends BaseEntity {

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
