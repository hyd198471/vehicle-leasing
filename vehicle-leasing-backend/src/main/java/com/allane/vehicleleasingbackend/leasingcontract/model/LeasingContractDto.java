package com.allane.vehicleleasingbackend.leasingcontract.model;

import com.allane.vehicleleasingbackend.customer.model.CustomerDto;
import com.allane.vehicleleasingbackend.vehicle.model.VehicleDto;

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

    private CustomerDto customerDto;

    private VehicleDto vehicleDto;

    private double monthlyRate;
}
