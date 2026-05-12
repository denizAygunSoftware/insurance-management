package com.insurance.management.company;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_COMPANY')")
    public ResponseEntity<String> create(@RequestBody @Valid CreateCompanyRequestDto dto) {
        companyService.createCompany(dto);
        return ResponseEntity.ok("Company created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EDIT_COMPANY')")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody @Valid CreateCompanyRequestDto dto) {
        companyService.updateCompany(id, dto);
        return ResponseEntity.ok("Company updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_COMPANY')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted");
    }
}
