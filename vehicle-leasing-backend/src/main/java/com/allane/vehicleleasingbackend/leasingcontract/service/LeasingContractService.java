package com.allane.vehicleleasingbackend.leasingcontract.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.allane.vehicleleasingbackend.common.Functionals;
import com.allane.vehicleleasingbackend.common.exception.NotFoundException;
import com.allane.vehicleleasingbackend.customer.model.CustomerDto;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerRepository;
import com.allane.vehicleleasingbackend.customer.service.CustomerService;
import com.allane.vehicleleasingbackend.leasingcontract.model.LeasingContractDto;
import com.allane.vehicleleasingbackend.leasingcontract.model.LeasingContractPayload;
import com.allane.vehicleleasingbackend.leasingcontract.persistence.LeasingContractEntity;
import com.allane.vehicleleasingbackend.leasingcontract.persistence.LeasingContractRepository;
import com.allane.vehicleleasingbackend.vehicle.model.VehicleDto;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleEntity;
import com.allane.vehicleleasingbackend.vehicle.persistence.VehicleRepository;
import com.allane.vehicleleasingbackend.vehicle.serivce.VehicleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeasingContractService {

    private final LeasingContractRepository leasingContractRepository;

    private final CustomerRepository customerRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleService vehicleService;

    private final CustomerService customerService;

    public LeasingContractDto getLeasingContractByContractNumber(long contractNumber) {
        LeasingContractEntity leasingContract = leasingContractRepository.findById(contractNumber).orElseThrow(
                NotFoundException::new);
        return mapToLeasingContractDto(leasingContract);
    }

    private LeasingContractDto mapToLeasingContractDto(LeasingContractEntity leasingContractEntity) {

        CustomerDto customerDto = customerService.mapToCustomerDto(leasingContractEntity.getCustomerEntity());
        VehicleDto vehicleDto = vehicleService.mapToVehicleDto(leasingContractEntity.getVehicleEntity());

        String customerInfo = customerDto.getFirstName() + " " + customerDto.getLastName();

        String vehicleInfo =
                vehicleDto.getBrand() + " " + vehicleDto.getModel() + " (" + vehicleDto.getModelYear() + ")";
        return LeasingContractDto.builder()
                .contractNumber(leasingContractEntity.getContractNumber())
                .monthlyRate(leasingContractEntity.getMonthlyRate().doubleValue())
                .customerInfo(customerInfo)
                .vehicleInfo(vehicleInfo)
                .vehicleNumber(vehicleDto.getVehicleNumber())
                .vehiclePrice(vehicleDto.getPrice())
                .details("hard link")
                .build();

    }

    public List<LeasingContractDto> listLeasingContracts() {
        List<LeasingContractEntity> leasingContractEntities = leasingContractRepository.findAll();
        return Functionals.mapItems(leasingContractEntities, this::mapToLeasingContractDto);
    }

    public LeasingContractDto updateLeasingContract(final long contract_number,
            LeasingContractPayload leasingContractPayload) {
        LeasingContractEntity leasingContractEntity = leasingContractRepository.save(
                this.mapToLeasingContractEntity(leasingContractPayload, Optional.of(contract_number)));
        return mapToLeasingContractDto(leasingContractEntity);
    }

    public LeasingContractDto createLeasingContract(final LeasingContractPayload leasingContractPayload) {
        LeasingContractEntity leasingContract =
                leasingContractRepository.save(
                        this.mapToLeasingContractEntity(leasingContractPayload, Optional.empty()));
        return mapToLeasingContractDto(leasingContract);
    }

    public LeasingContractEntity mapToLeasingContractEntity(LeasingContractPayload leasingContractPayload,
            Optional<Long> contractNumberOpt) {

        CustomerEntity customerEntity =
                customerRepository.findById(leasingContractPayload.getCustomerId()).orElseThrow(NotFoundException::new);

        VehicleEntity vehicleEntity =
                vehicleRepository.findById(leasingContractPayload.getVehicleId()).orElseThrow(NotFoundException::new);

        LeasingContractEntity.LeasingContractEntityBuilder leasingContractEntityBuilder =
                LeasingContractEntity.builder()
                        .monthlyRate(BigDecimal.valueOf(leasingContractPayload.getMonthlyRate()).stripTrailingZeros())
                        .customerEntity(customerEntity)
                        .vehicleEntity(vehicleEntity);
        if (contractNumberOpt.isPresent()) {
            return leasingContractEntityBuilder
                    .contractNumber(contractNumberOpt.get()).build();
        } else {
            return leasingContractEntityBuilder.build();
        }
    }
}
