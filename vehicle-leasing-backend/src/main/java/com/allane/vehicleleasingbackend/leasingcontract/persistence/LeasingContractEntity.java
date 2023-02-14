package com.allane.vehicleleasingbackend.leasingcontract.persistence;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleEntity;

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
