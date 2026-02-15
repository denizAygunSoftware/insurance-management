package com.insurance.management.branch;

import com.insurance.management.common.entity.BaseEntity;
import com.insurance.management.company.Company;
import jakarta.persistence.*;

@Entity
@Table(name = "branches",
        indexes = {
                @Index(name = "idx_branch_company", columnList = "company_id")
        })
public class Branch extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String branchName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Branch(){

    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
