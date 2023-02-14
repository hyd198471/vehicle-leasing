package com.allane.vehicleleasingbackend.vehicle.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allane.vehicleleasingbackend.vehicle.model.VehicleDto;
import com.allane.vehicleleasingbackend.vehicle.serivce.VehicleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/{id}")
    public VehicleDto findById(@PathVariable long id) {
        return vehicleService.findById(id);
    }

    @GetMapping("/")
    public List<VehicleDto> listVehicles() {
        return vehicleService.listVehicles();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDto updateVehicle(@PathVariable("id") final long id, @RequestBody final VehicleDto vehicle) {
        return vehicleService.updateVehicle(id, vehicle);

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto createVehicle(@NotNull @Valid @RequestBody final VehicleDto vehicle) {
        return vehicleService.createVehicle(vehicle);
    }
}
