package com.allane.vehicleleasingbackend.leasingcontract.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allane.vehicleleasingbackend.leasingcontract.model.LeasingContractDto;
import com.allane.vehicleleasingbackend.leasingcontract.model.LeasingContractPayload;
import com.allane.vehicleleasingbackend.leasingcontract.service.LeasingContractService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leasingcontract")
@RequiredArgsConstructor
public class LeasingContractController {

    private final LeasingContractService leasingContractService;

    @GetMapping("/{contractNumber}")
    public LeasingContractDto findByContractNumber(@PathVariable long contractNumber) {
        return leasingContractService.getLeasingContractByContractNumber((contractNumber));
    }

    @GetMapping("/")
    public List<LeasingContractDto> listLeasingContracts() {
        return leasingContractService.listLeasingContracts();
    }

    @PutMapping("/{contractNumber}")
    @ResponseStatus(HttpStatus.OK)
    public LeasingContractDto updateLeasingContract(@PathVariable long contractNumber,
            @RequestBody final LeasingContractPayload leasingContract) {
        return leasingContractService.updateLeasingContract(contractNumber, leasingContract);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public LeasingContractDto createLeasingContract(
            @NotNull @Valid @RequestBody final LeasingContractPayload leasingContract) {
        return leasingContractService.createLeasingContract(leasingContract);
    }

}
