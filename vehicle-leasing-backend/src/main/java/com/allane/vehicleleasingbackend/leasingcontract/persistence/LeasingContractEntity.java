package com.allane.vehicleleasingbackend.leasingcontract.persistence;

import java.math.BigDecimal;

import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LEASING_CONTRACT")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class LeasingContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTRACT_NUMBER")
    private Long contractNumber;

    @Column(name = "MONTHLY_RATE", precision = 18, scale = 2)
    @NotNull
    private BigDecimal monthlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id")
    private CustomerEntity customerEntity;

    @OneToOne
    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "id")
    private VehicleEntity vehicleEntity;

}
