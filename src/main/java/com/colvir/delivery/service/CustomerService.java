package com.colvir.delivery.service;

import com.colvir.delivery.dto.CustomerDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface CustomerService {
    @GetMapping("/get/{name}")
    Optional<CustomerDto> findByName(@PathVariable("name") String name);
}
