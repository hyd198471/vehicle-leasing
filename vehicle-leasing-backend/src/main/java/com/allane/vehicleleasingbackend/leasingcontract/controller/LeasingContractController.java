package com.allane.vehicleleasingbackend.leasingcontract.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allane.vehicleleasingbackend.leasingcontract.model.LeasingContractDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leasingcontract")
@RequiredArgsConstructor
public class LeasingContractController {

    @GetMapping("/{contractNumber}")
    public LeasingContractDto findByContractNumber(@PathVariable long contractNumber) {
        return null;
    }
}
