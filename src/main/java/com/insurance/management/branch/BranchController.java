package com.insurance.management.branch;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService){
        this.branchService = branchService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_BRANCH')")
    public ResponseEntity<String> createBranch(@RequestBody @Valid CreateBranchRequestDto dto){
        branchService.createBranch(dto);
        return ResponseEntity.ok("Branch Created");
    }


}
