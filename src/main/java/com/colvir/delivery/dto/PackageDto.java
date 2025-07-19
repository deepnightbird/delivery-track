package com.colvir.delivery.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageDto {

    private Long id;
    private String trackingNumber;
    private String description;
    private float weight;
    private Long idPackageStatus;
    private Long idPackageSender;
    private Long idPackageRecepient;
    private LocalDateTime createdAt;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveredAt;

    public PackageDto(Long id, String trackingNumber, String description, float weight, PackageStatusDto statusDto, CustomerDto senderDto, CustomerDto recepientDto) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.description = description;
        this.weight = weight;
        this.idPackageStatus = statusDto.getId();
        this.idPackageSender = senderDto.getId();
        this.idPackageRecepient = recepientDto.getId();
    }
}