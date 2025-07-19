package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.model.TrackingEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TrackingEventMapper {

    TrackingEventDto toDto(TrackingEvent entity);

    TrackingEvent toEntity(TrackingEventDto dto);

    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Named("parseDateTime")
    default LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // удалить !!!
    /*default void updateEntityFromDto(TrackingEventDto dto, @MappingTarget TrackingEvent entity) {
        if (dto == null) {
            return;
        }

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getLocation() != null) {
            entity.setLocation(dto.getLocation());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getEventTime() != null) {
            entity.setEventTime(parseDateTime(dto.getEventTime()));
        }
    }*/
}