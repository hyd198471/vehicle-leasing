package com.allane.vehicleleasingbackend.vehicle.serivce;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.allane.vehicleleasingbackend.common.Functionals;
import com.allane.vehicleleasingbackend.common.exception.NotFoundException;
import com.allane.vehicleleasingbackend.vehicle.model.VehicleDto;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleEntity;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleDto findById(long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToVehicleDto(vehicleEntity);
    }

    public List<VehicleDto> listVehicles() {
        List<VehicleEntity> vehicleEntities = vehicleRepository.findAll();
        return Functionals.mapItems(vehicleEntities, this::mapToVehicleDto);
    }

    public VehicleDto mapToVehicleDto(VehicleEntity vehicleEntity) {
        return VehicleDto.builder()
                .brand(vehicleEntity.getBrand())
                .model(vehicleEntity.getModel())
                .modelYear(vehicleEntity.getModelYear())
                .vehicleNumber(vehicleEntity.getVehicleNumber())
                .price(vehicleEntity.getPrice().doubleValue()).build();

    }

    public VehicleEntity mapToVehicleEntity(VehicleDto vehicleDto, Optional<Long> idOpt) {
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

    public VehicleDto updateVehicle(final long id, final VehicleDto vehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.save(this.mapToVehicleEntity(vehicle, Optional.of(id)));
        return mapToVehicleDto(vehicleEntity);

    }

    public VehicleDto createVehicle(final VehicleDto vehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.save(this.mapToVehicleEntity(vehicle, Optional.empty()));
        return mapToVehicleDto(vehicleEntity);
    }
}
