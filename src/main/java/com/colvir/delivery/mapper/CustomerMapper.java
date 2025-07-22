package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.model.Customer;
import com.colvir.delivery.model.PackageStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDto customerDto);

    CustomerDto toDto(Customer customer);

}
