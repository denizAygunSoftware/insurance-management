package com.insurance.management.insurance;

import com.insurance.management.branch.Branch;
import com.insurance.management.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(
        name = "insurance_policies",
        indexes = {
                @Index(name = "idx_policy_identity", columnList = "identityNumber"),
                @Index(name = "idx_policy_end_date", columnList = "endDate"),
                @Index(name = "idx_policy_branch", columnList = "branch_id")
        }
)
public class InsurancePolicy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_type_id", nullable = false)
    private InsuranceType insuranceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Pattern(regexp = "^[0-9]{11}$")
    @Column(nullable = false, length = 11)
    private String identityNumber;

    @Column(length = 20)
    private String plateNumber;

    @Column(length = 50)
    private String chassisNumber;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String ownerName;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, unique = true, length = 50)
    private String policyNumber;

    public InsurancePolicy() {
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber (String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public @NotBlank String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
}
