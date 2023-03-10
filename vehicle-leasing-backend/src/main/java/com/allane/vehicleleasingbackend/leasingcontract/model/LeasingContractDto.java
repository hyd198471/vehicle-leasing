package com.allane.vehicleleasingbackend.leasingcontract.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class LeasingContractDto {

    private long contractNumber;

    private String customerInfo;

    private String vehicleInfo;

    private String vehicleNumber;

    private double monthlyRate;

    private String details;

    private double vehiclePrice;
}
