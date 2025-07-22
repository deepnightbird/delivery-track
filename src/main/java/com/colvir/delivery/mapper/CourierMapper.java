package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.CourierDto;
import com.colvir.delivery.model.Courier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierMapper {
    Courier toEntity(CourierDto courierDto);

    CourierDto toDto(Courier courier);
}
