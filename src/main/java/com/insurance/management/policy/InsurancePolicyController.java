package com.insurance.management.policy;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
public class InsurancePolicyController {

    private final InsurancePolicyService policyService;

    public InsurancePolicyController(InsurancePolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_POLICY')")
    public ResponseEntity<String> create(@RequestBody @Valid CreateInsurancePolicyRequestDto dto) {
        policyService.createPolicy(dto);
        return ResponseEntity.ok("Policy created");
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicyResponseDto>> list(
            @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(policyService.listPolicies(branchId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_POLICY')")
    public ResponseEntity<String> update(@PathVariable Long id,
                                          @RequestBody @Valid CreateInsurancePolicyRequestDto dto) {
        policyService.updatePolicy(id, dto);
        return ResponseEntity.ok("Policy updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_POLICY')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.ok("Policy deleted");
    }
}
