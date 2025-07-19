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
    private PackageStatusDto packageStatus;
    private Package pkg;

    public String getTrackingNumber() {
        return pkg.getTrackingNumber();
    }

    public void setTrackingNumber(String trackingNumber) {
        pkg.setTrackingNumber(trackingNumber);
    }

}