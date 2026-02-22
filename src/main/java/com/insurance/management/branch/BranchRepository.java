package com.insurance.management.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    boolean existsByBranchNameAndCompany_Id(String branchName, Long companyId);


}
