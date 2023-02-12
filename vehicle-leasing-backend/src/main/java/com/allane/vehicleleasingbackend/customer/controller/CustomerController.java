package com.allane.vehicleleasingbackend.customer.controller;

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
import com.allane.vehicleleasingbackend.customer.exception.CustomerNotFoundException;
import com.allane.vehicleleasingbackend.customer.model.CustomerDto;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return mapToCustomerDto(customerEntity);
    }

    @GetMapping("/")
    public List<CustomerDto> listCustomers() {
        List<CustomerEntity> customerEntity = customerRepository.findAll();
        return Functionals.mapItems(customerEntity, this::mapToCustomerDto);
    }

    private CustomerDto mapToCustomerDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .lastName(customerEntity.getLastName())
                .firstName(customerEntity.getFirstName()).build();

    }

    private CustomerEntity mapToCustomerEntity(CustomerDto customerDto, Optional<Long> idOpt) {
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto updateCustomer(@PathVariable("id") final long id, @RequestBody final CustomerDto customer) {
        CustomerEntity customerEntity = customerRepository.save(this.mapToCustomerEntity(customer, Optional.of(id)));
        return mapToCustomerDto(customerEntity);

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@NotNull @Valid @RequestBody final CustomerDto customer) {
        CustomerEntity customerEntity = customerRepository.save(this.mapToCustomerEntity(customer, Optional.empty()));
        return mapToCustomerDto(customerEntity);
    }
}
