package com.allane.vehicleleasingbackend.customer.controller;

import java.util.List;
import java.util.Optional;

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

import com.allane.vehicleleasingbackend.customer.model.CustomerDto;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerEntity;
import com.allane.vehicleleasingbackend.customer.persistence.CustomerRepository;
import com.allane.vehicleleasingbackend.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDto findById(@PathVariable long id) {
        return customerService.findById(id);
    }

    @GetMapping("/")
    public List<CustomerDto> listCustomers() {
        return customerService.listCustomers();
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
        return customerService.updateCustomer(id, customer);

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@NotNull @Valid @RequestBody final CustomerDto customer) {
        return customerService.createCustomer(customer);
    }
}
