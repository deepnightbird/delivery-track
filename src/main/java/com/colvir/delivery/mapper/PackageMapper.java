package com.colvir.delivery.mapper;

import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.model.Customer;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PackageMapper {

    PackageStatusMapper packageStatusMapper = Mappers.getMapper(PackageStatusMapper.class);
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    Package toEntity(PackageDto packageDto);

    PackageDto toDto(Package pkg);

    @AfterMapping
    default void mapPackageStatus(@MappingTarget PackageDto packageDto, Package pkg) {
        if (pkg.getStatus() != null) {
            PackageStatusDto packageStatusDto = packageStatusMapper.toDto(pkg.getStatus());
            packageDto.setIdPackageStatus(pkg.getStatus().getId());
            packageDto.setPackageStatusDto(packageStatusDto);
        }
        if (pkg.getSender() != null) {
            CustomerDto customerDto = customerMapper.toDto(pkg.getSender());
            packageDto.setSenderDto(customerDto);
            packageDto.setIdPackageSender(customerDto.getId());
        }
        if (pkg.getRecipient() != null) {
            CustomerDto customerDto = customerMapper.toDto(pkg.getRecipient());
            packageDto.setRecipientDto(customerDto);
            packageDto.setIdPackageRecipient(customerDto.getId());
        }
    }

    @AfterMapping
    default void mapPackageSender(@MappingTarget PackageDto packageDto, Package pkg) {
        if (pkg.getSender() == null) {
            return;
        }
        packageDto.setIdPackageSender(pkg.getSender().getId());
    }

    @AfterMapping
    default void mapPackageRecipient(@MappingTarget PackageDto packageDto, Package pkg) {
        if (pkg.getRecipient() == null) {
            return;
        }
        packageDto.setIdPackageRecipient(pkg.getRecipient().getId());
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
    default void mapPackageRecipient(@MappingTarget Package pkg, PackageDto packageDto) {
        if (packageDto.getIdPackageSender() == null) {
            return;
        }
        Customer recipient = new Customer();
        recipient.setId(packageDto.getIdPackageRecipient());
        pkg.setRecipient(recipient);
    }
}
