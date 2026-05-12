package com.insurance.management.company;

import com.insurance.management.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void createCompany(CreateCompanyRequestDto dto) {
        if (companyRepository.existsByCompanyCode(dto.getCompanyCode())) {
            throw new BusinessException("Company code already exists");
        }
        Company company = new Company();
        company.setCompanyName(dto.getCompanyName().trim());
        company.setCompanyCode(dto.getCompanyCode().trim());
        companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(Long id, CreateCompanyRequestDto dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Company not found"));

        if (companyRepository.existsByCompanyCodeAndIdNot(dto.getCompanyCode(), id)) {
            throw new BusinessException("Company code already exists");
        }

        company.setCompanyName(dto.getCompanyName().trim());
        company.setCompanyCode(dto.getCompanyCode().trim());
        companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new BusinessException("Company not found");
        }
        companyRepository.deleteById(id);
    }
}
