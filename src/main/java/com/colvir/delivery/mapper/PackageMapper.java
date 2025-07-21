package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.model.Customer;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper
public interface PackageMapper {

    Package toEntity(PackageDto packageDto);

    PackageDto toDto(Package pkg);

    default Optional<PackageDto> toOptional(Optional<Package> pkg) {
        return pkg.map(this::toDto);
    }

    @AfterMapping
    default void mapPackageStatus(@MappingTarget PackageDto packageDto, Package pkg) {
        if (pkg.getStatus() == null) {
            return;
        }
        packageDto.setIdPackageStatus(pkg.getStatus().getId());
    }

    @AfterMapping
    default void mapPackageSender(@MappingTarget PackageDto packageDto, Customer customer) {
        if (customer.getId() == null) {
            return;
        }
        packageDto.setIdPackageSender(customer.getId());
    }

    @AfterMapping
    default void mapPackageRecepient(@MappingTarget PackageDto packageDto, Customer customer) {
        if (customer.getId() == null) {
            return;
        }
        packageDto.setIdPackageRecepient(customer.getId());
    }

    @AfterMapping
    default void mapPackageStatus(@MappingTarget Package pkg, PackageDto packageDto) {
        if (packageDto.getIdPackageStatus() == null) {
            return;
        }
        PackageStatus packageStatus = new PackageStatus();
        packageStatus.setId(packageDto.getIdPackageStatus());
        pkg.setStatus(packageStatus);
    }

    @AfterMapping
    default void mapPackageSender(@MappingTarget Package pkg, PackageDto packageDto) {
        if (packageDto.getIdPackageSender() == null) {
            return;
        }
        Customer sender = new Customer();
        sender.setId(packageDto.getIdPackageSender());
        pkg.setSender(sender);
    }

    @AfterMapping
    default void mapPackageRecepient(@MappingTarget Package pkg, PackageDto packageDto) {
        if (packageDto.getIdPackageSender() == null) {
            return;
        }
        Customer recepient = new Customer();
        recepient.setId(packageDto.getIdPackageRecepient());
        pkg.setSender(recepient);
    }
}
