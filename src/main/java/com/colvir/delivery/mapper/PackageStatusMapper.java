package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.model.PackageStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PackageStatusMapper {

    PackageStatus toEntity(PackageStatusDto packageStatusDto);

    PackageStatusDto toDto(PackageStatus packageStatus);
}
