package com.insurance.management.insurance;

import com.insurance.management.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsuranceTypeService {

    private final InsuranceTypeRepository insuranceTypeRepository;

    public InsuranceTypeService(InsuranceTypeRepository insuranceTypeRepository) {
        this.insuranceTypeRepository = insuranceTypeRepository;
    }

    @Transactional
    public void createInsuranceType(CreateInsuranceTypeRequestDto dto) {
        if (insuranceTypeRepository.existsByInsuranceName(dto.getInsuranceName())) {
            throw new BusinessException("Insurance type already exists");
        }
        InsuranceType type = new InsuranceType();
        type.setInsuranceName(dto.getInsuranceName().trim());
        type.setCategory(dto.getCategory());
        insuranceTypeRepository.save(type);
    }

    @Transactional(readOnly = true)
    public List<InsuranceType> listInsuranceTypes() {
        return insuranceTypeRepository.findAll();
    }
}
