package com.insurance.management.branch;

import com.insurance.management.common.exception.BusinessException;
import com.insurance.management.company.Company;
import com.insurance.management.company.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;

    public BranchService(BranchRepository branchRepository, CompanyRepository companyRepository) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void createBranch(CreateBranchRequestDto branchRequestDto) {

        Company company = companyRepository.findById(branchRequestDto.getCompanyId())
                .orElseThrow(() -> new BusinessException("Company not found"));

        if (branchRepository.existsByBranchNameAndCompany_Id(branchRequestDto.getBranchName(), branchRequestDto.getCompanyId())) {
            throw new BusinessException("Branch already exists for this company");
        }
        Branch branch = new Branch();
        branch.setBranchName(branchRequestDto.getBranchName());
        branch.setCompany(company);

        branchRepository.save(branch);

    }

}
