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
public class LeasingContractPayload {

    private long customerId;

    private long vehicleId;

    private double monthlyRate;
}
