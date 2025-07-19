package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.model.Package;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface PackageMapper {
    PackageDto toDto(Package pkg);

    default Optional<PackageDto> toOptional(Optional<Package> pkg) {
        return pkg.map(this::toDto);
    }
}
