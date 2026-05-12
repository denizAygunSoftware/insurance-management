package com.insurance.management.policy;

import com.insurance.management.insurance.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    boolean existsByPolicyNumber(String policyNumber);
    boolean existsByPolicyNumberAndIdNot(String policyNumber, Long id);
    List<InsurancePolicy> findByBranch_Id(Long branchId);
}
