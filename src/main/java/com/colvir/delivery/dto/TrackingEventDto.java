package com.colvir.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.colvir.delivery.model.Package;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventDto{

    private Long id;
    private String location;
    private String eventName;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private CourierDto courierDto;
    private Long courierId;
    private PackageDto packageDto;
    private Long packageId;
    private PackageStatusDto packageStatusDto;
    private Long packageStatusId;

    public String getTrackingNumber() {
        if (this.packageDto == null) {
            return null;
        }
        return packageDto.getTrackingNumber();
    }

    public void setTrackingNumber(String trackingNumber) {
        if (this.packageDto != null) {
            packageDto.setTrackingNumber(trackingNumber);
        }
    }

}