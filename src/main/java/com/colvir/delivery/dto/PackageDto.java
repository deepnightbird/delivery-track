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
    private Long id_package_status;
    private Long id_package_sender;
    private Long id_package_recepient;
    private LocalDateTime createdAt;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime deliveredAt;
}