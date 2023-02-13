package com.allane.vehicleleasingbackend.leasingcontract.service;

import org.springframework.stereotype.Service;

import com.allane.vehicleleasingbackend.leasingcontract.persistence.LeasingContractRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeasingContractService {

    private final LeasingContractRepository leasingContractRepository;
}
