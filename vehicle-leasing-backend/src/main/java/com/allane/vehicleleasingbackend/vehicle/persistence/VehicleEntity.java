package com.allane.vehicleleasingbackend.vehicle.persistence;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VEHICLE")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BRAND")
    @NotBlank
    private String brand;

    @Column(name = "MODEL")
    @NotBlank
    private String model;

    @Column(name = "MODEL_YEAR")
    @NotNull
    private int modelYear;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Column(name = "PRICE", precision = 18, scale = 2)
    @NotNull
    private BigDecimal price;

}
