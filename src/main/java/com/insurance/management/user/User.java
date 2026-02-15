package com.insurance.management.user;

import com.insurance.management.branch.Branch;
import com.insurance.management.common.entity.BaseEntity;
import com.insurance.management.company.Company;
import com.insurance.management.security.Permissions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_identity", columnList = "identityNumber")
        })
public class User extends BaseEntity {

    @Email
    @NotBlank
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank()
    @Column(nullable = false, length = 100)
    private String password;

    @Pattern(regexp = "^[0-9]{11}$")
    @Column(nullable = false, length = 11)
    private String identityNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "company_id", nullable=false)
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "user_branch",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private Set<Branch> branches = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permissions> permissions = new HashSet<>();

    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branch> branches) {
        this.branches = branches;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
