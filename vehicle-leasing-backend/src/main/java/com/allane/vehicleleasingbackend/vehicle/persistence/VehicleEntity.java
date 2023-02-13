package com.allane.vehicleleasingbackend.vehicle.persistence;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
