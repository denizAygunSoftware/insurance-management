package com.insurance.management.user;

import com.insurance.management.branch.Branch;
import com.insurance.management.branch.BranchRepository;
import com.insurance.management.company.Company;
import com.insurance.management.company.CompanyRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserService(BCryptPasswordEncoder passwordEncoder,UserRepository userRepository,CompanyRepository companyRepository,BranchRepository branchRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository =  userRepository;
        this.companyRepository = companyRepository;
        this.branchRepository = branchRepository;
    }

    public String createUser(CreateUserRequestDto userRequestDto) {

        Company company = companyRepository.findById(userRequestDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Set<Branch> branches = new HashSet<>(branchRepository.findAllById(userRequestDto.getBranchIds()));

        if(branches.size() != userRequestDto.getBranchIds().size()){
            throw new RuntimeException("Some branches not found.");
        }

        boolean invalidBranch = branches.stream()
                .anyMatch(branch -> !branch.getCompany().getId().equals(company.getId()));

        if(invalidBranch){
            throw new RuntimeException("Branch does not belong to company.");
        }

        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setCompany(company);
        user.setBranches(branches);
        user.setIdentityNumber(userRequestDto.getIdentityNumber());
        user.setPermissions(userRequestDto.getPermissions());

        String passwordTemp = generateTempPassword();
        user.setPassword(passwordEncoder.encode(passwordTemp));
        user.setMustChangePassword(true);

        userRepository.save(user);

        return  passwordTemp;
    }

    private String generateTempPassword(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[9];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
