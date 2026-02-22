package com.insurance.management.user;

import com.insurance.management.security.Permissions;

import java.util.HashSet;
import java.util.Set;

public class CreateUserRequestDto {

    private String email;
    private String identityNumber;
    private Long companyId;
    private Set<Long> branchIds = new HashSet<>();
    private Set<Permissions> permissions = new HashSet<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Set<Long> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(Set<Long> branchIds) {
        this.branchIds = branchIds;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
