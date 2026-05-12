package com.insurance.management.policy;

import com.insurance.management.branch.Branch;
import com.insurance.management.branch.BranchRepository;
import com.insurance.management.common.exception.BusinessException;
import com.insurance.management.insurance.InsurancePolicy;
import com.insurance.management.insurance.InsuranceType;
import com.insurance.management.insurance.InsuranceTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsurancePolicyService {

    private final InsurancePolicyRepository policyRepository;
    private final BranchRepository branchRepository;
    private final InsuranceTypeRepository insuranceTypeRepository;

    public InsurancePolicyService(InsurancePolicyRepository policyRepository,
                                   BranchRepository branchRepository,
                                   InsuranceTypeRepository insuranceTypeRepository) {
        this.policyRepository = policyRepository;
        this.branchRepository = branchRepository;
        this.insuranceTypeRepository = insuranceTypeRepository;
    }

    @Transactional
    public void createPolicy(CreateInsurancePolicyRequestDto dto) {
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new BusinessException("Branch not found"));

        InsuranceType insuranceType = insuranceTypeRepository.findById(dto.getInsuranceTypeId())
                .orElseThrow(() -> new BusinessException("Insurance type not found"));

        if (policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            throw new BusinessException("Policy number already exists");
        }

        if (!dto.getStartDate().isBefore(dto.getEndDate())) {
            throw new BusinessException("Start date must be before end date");
        }

        InsurancePolicy policy = new InsurancePolicy();
        mapDtoToEntity(dto, policy, branch, insuranceType);
        policyRepository.save(policy);
    }

    @Transactional(readOnly = true)
    public List<InsurancePolicyResponseDto> listPolicies(Long branchId) {
        List<InsurancePolicy> policies = branchId != null
                ? policyRepository.findByBranch_Id(branchId)
                : policyRepository.findAll();
        return policies.stream().map(this::toDto).toList();
    }

    @Transactional
    public void updatePolicy(Long id, CreateInsurancePolicyRequestDto dto) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Policy not found"));

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new BusinessException("Branch not found"));

        InsuranceType insuranceType = insuranceTypeRepository.findById(dto.getInsuranceTypeId())
                .orElseThrow(() -> new BusinessException("Insurance type not found"));

        if (policyRepository.existsByPolicyNumberAndIdNot(dto.getPolicyNumber(), id)) {
            throw new BusinessException("Policy number already exists");
        }

        if (!dto.getStartDate().isBefore(dto.getEndDate())) {
            throw new BusinessException("Start date must be before end date");
        }

        mapDtoToEntity(dto, policy, branch, insuranceType);
        policyRepository.save(policy);
    }

    @Transactional
    public void deletePolicy(Long id) {
        InsurancePolicy policy = policyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Policy not found"));
        policy.setDeletedAt(LocalDateTime.now());
        policyRepository.save(policy);
    }

    private void mapDtoToEntity(CreateInsurancePolicyRequestDto dto, InsurancePolicy policy,
                                 Branch branch, InsuranceType insuranceType) {
        policy.setBranch(branch);
        policy.setInsuranceType(insuranceType);
        policy.setIdentityNumber(dto.getIdentityNumber());
        policy.setPlateNumber(dto.getPlateNumber());
        policy.setChassisNumber(dto.getChassisNumber());
        policy.setOwnerName(dto.getOwnerName().trim());
        policy.setPhone(dto.getPhone());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPolicyNumber(dto.getPolicyNumber().trim());
    }

    private InsurancePolicyResponseDto toDto(InsurancePolicy policy) {
        InsurancePolicyResponseDto dto = new InsurancePolicyResponseDto();
        dto.setId(policy.getId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setOwnerName(policy.getOwnerName());
        dto.setIdentityNumber(policy.getIdentityNumber());
        dto.setPlateNumber(policy.getPlateNumber());
        dto.setChassisNumber(policy.getChassisNumber());
        dto.setPhone(policy.getPhone());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setBranchId(policy.getBranch().getId());
        dto.setBranchName(policy.getBranch().getBranchName());
        dto.setInsuranceTypeId(policy.getInsuranceType().getId());
        dto.setInsuranceName(policy.getInsuranceType().getInsuranceName());
        dto.setCategory(policy.getInsuranceType().getCategory());
        return dto;
    }
}
