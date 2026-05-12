package com.insurance.management.user;

import com.insurance.management.branch.Branch;
import com.insurance.management.branch.BranchRepository;
import com.insurance.management.common.exception.BusinessException;
import com.insurance.management.company.Company;
import com.insurance.management.company.CompanyRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;

    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                       CompanyRepository companyRepository, BranchRepository branchRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public String createUser(CreateUserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new BusinessException("Company not found"));

        Set<Branch> branches = new HashSet<>(branchRepository.findAllById(dto.getBranchIds()));

        if (branches.size() != dto.getBranchIds().size()) {
            throw new BusinessException("Some branches not found");
        }

        boolean invalidBranch = branches.stream()
                .anyMatch(branch -> !branch.getCompany().getId().equals(company.getId()));

        if (invalidBranch) {
            throw new BusinessException("Branch does not belong to company");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setCompany(company);
        user.setBranches(branches);
        user.setIdentityNumber(dto.getIdentityNumber());
        user.setPermissions(dto.getPermissions());

        String tempPassword = generateTempPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setMustChangePassword(true);

        userRepository.save(user);

        return tempPassword;
    }

    private String generateTempPassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[9];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
