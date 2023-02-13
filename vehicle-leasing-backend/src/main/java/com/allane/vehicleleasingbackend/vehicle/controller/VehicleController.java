package com.allane.vehicleleasingbackend.vehicle.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.allane.vehicleleasingbackend.common.Functionals;
import com.allane.vehicleleasingbackend.common.exception.NotFoundException;
import com.allane.vehicleleasingbackend.vehicle.model.VehicleDto;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleEntity;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleRepository vehicleRepository;

    @GetMapping("/{id}")
    public VehicleDto findById(@PathVariable long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToVehicleDto(vehicleEntity);
    }

    @GetMapping("/")
    public List<VehicleDto> listVehicles() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();
        return Functionals.mapItems(vehicleEntities, this::mapToVehicleDto);
    }

    private VehicleDto mapToVehicleDto(VehicleEntity vehicleEntity) {
        return VehicleDto.builder()
                .brand(vehicleEntity.getBrand())
                .model(vehicleEntity.getModel())
                .modelYear(vehicleEntity.getModelYear())
                .vehicleNumber(vehicleEntity.getVehicleNumber())
                .price(vehicleEntity.getPrice().doubleValue()).build();

    }

    private VehicleEntity mapToVehicleEntity(VehicleDto vehicleDto, Optional<Long> idOpt) {
        VehicleEntity.VehicleEntityBuilder vehicleEntityBuilder = VehicleEntity.builder()
                .brand(vehicleDto.getBrand())
                .model(vehicleDto.getModel())
                .modelYear(vehicleDto.getModelYear())
                .vehicleNumber(vehicleDto.getVehicleNumber())
                .price(BigDecimal.valueOf(vehicleDto.getPrice()).stripTrailingZeros());
        if (idOpt.isPresent()) {
            return vehicleEntityBuilder
                    .id(idOpt.get()).build();
        } else {
            return vehicleEntityBuilder.build();
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDto updateVehicle(@PathVariable("id") final long id, @RequestBody final VehicleDto vehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.save(this.mapToVehicleEntity(vehicle, Optional.of(id)));
        return mapToVehicleDto(vehicleEntity);

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto createVehicle(@NotNull @Valid @RequestBody final VehicleDto vehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.save(this.mapToVehicleEntity(vehicle, Optional.empty()));
        return mapToVehicleDto(vehicleEntity);
    }
}
