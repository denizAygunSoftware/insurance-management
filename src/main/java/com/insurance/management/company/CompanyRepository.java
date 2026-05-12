package com.insurance.management.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyCode(String companyCode);
    boolean existsByCompanyCodeAndIdNot(String companyCode, Long id);
}
