package com.allane.vehicleleasingbackend.vehicle.model;

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
public class VehicleDto {

    private String brand;

    private String model;

    private int modelYear;

    private String vehicleNumber;

    private double price;
}
