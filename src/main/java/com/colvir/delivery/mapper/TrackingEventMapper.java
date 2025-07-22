package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.CourierDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.PackageStatusNotFoundException;
import com.colvir.delivery.model.Courier;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import com.colvir.delivery.repository.PackageRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TrackingEventMapper {

    PackageMapper packageMapper = Mappers.getMapper(PackageMapper.class);
    CourierMapper courierMapper = Mappers.getMapper(CourierMapper.class);
    PackageStatusMapper packageStatusMapper = Mappers.getMapper(PackageStatusMapper.class);

    TrackingEventDto toDto(TrackingEvent entity);

    TrackingEvent toEntity(TrackingEventDto dto);

    @AfterMapping
    default void afterMapping(@MappingTarget TrackingEventDto trackingEventDto, TrackingEvent entity) {
        if (entity.getPkg().getId() != null) {
            PackageDto packageDto = packageMapper.toDto(entity.getPkg());
            trackingEventDto.setPackageId(entity.getPkg().getId());
            trackingEventDto.setPackageDto(packageDto);
        }

        if (entity.getCourier() != null) {
            CourierDto courierDto = courierMapper.toDto(entity.getCourier());
            trackingEventDto.setCourierId(entity.getCourier().getId());
            trackingEventDto.setCourierDto(courierDto);
        }

        if (entity.getStatus() != null) {
            PackageStatusDto packageStatusDto = packageStatusMapper.toDto(entity.getStatus());
            trackingEventDto.setPackageStatusId(entity.getStatus().getId());
            trackingEventDto.setPackageStatusDto(packageStatusDto);
        }
    }

    @AfterMapping
    default void afterMapping(@MappingTarget TrackingEvent trackingEvent, TrackingEventDto trackingEventDto) {
        if (trackingEventDto.getPackageId() != null) {
            Package pkg = new Package();
            pkg.setId(trackingEventDto.getPackageId());
            trackingEvent.setPkg(pkg);
        }

        if (trackingEventDto.getCourierId() != null) {
            Courier courier = new Courier();
            courier.setId(trackingEventDto.getCourierId());
            trackingEvent.setCourier(courier);
        }

        if (trackingEventDto.getPackageStatusId() != null) {
            //packageStatus.setName(trackingEventDto.getPackageStatusDto().getName());
            //trackingEvent.setStatus(packageStatus);
        }
    }

    List<TrackingEventDto> toDtos(List<TrackingEvent> trackingEventDto);

}