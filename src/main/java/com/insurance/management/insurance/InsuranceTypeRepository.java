package com.insurance.management.insurance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceTypeRepository extends JpaRepository<InsuranceType, Long> {
    boolean existsByInsuranceName(String insuranceName);
}
