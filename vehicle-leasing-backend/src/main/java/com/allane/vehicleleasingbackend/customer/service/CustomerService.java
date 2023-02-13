package com.allane.vehicleleasingbackend.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.allane.vehicleleasingbackend.common.Functionals;
import com.allane.vehicleleasingbackend.common.exception.NotFoundException;
import com.allane.vehicleleasingbackend.customer.model.CustomerDto;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto findById(long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToCustomerDto(customerEntity);
    }

    public List<CustomerDto> listCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return Functionals.mapItems(customerEntities, this::mapToCustomerDto);
    }

    public CustomerDto updateCustomer(final long id, CustomerDto customer) {
        CustomerEntity customerEntity = customerRepository.save(this.mapToCustomerEntity(customer, Optional.of(id)));
        return mapToCustomerDto(customerEntity);
    }

    public CustomerDto createCustomer(final CustomerDto customer) {
        CustomerEntity customerEntity = customerRepository.save(this.mapToCustomerEntity(customer, Optional.empty()));
        return mapToCustomerDto(customerEntity);
    }

    public CustomerDto mapToCustomerDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .lastName(customerEntity.getLastName())
                .firstName(customerEntity.getFirstName()).build();

    }

    public CustomerEntity mapToCustomerEntity(CustomerDto customerDto, Optional<Long> idOpt) {
        CustomerEntity.CustomerEntityBuilder customerEntityBuilder = CustomerEntity.builder()
                .lastName(customerDto.getLastName())
                .firstName(customerDto.getFirstName());
        if (idOpt.isPresent()) {
            return customerEntityBuilder
                    .id(idOpt.get()).build();
        } else {
            return customerEntityBuilder.build();
        }
    }
}
