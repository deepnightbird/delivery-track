package com.colvir.delivery.service.impl;

import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.mapper.CustomerMapper;
import com.colvir.delivery.repository.CustomerRepository;
import com.colvir.delivery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public Optional<CustomerDto> findByName(@PathVariable("name") String name){
        return customerRepository.findByName(name).map(customerMapper::toDto);
    }
}
