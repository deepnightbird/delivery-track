package com.colvir.delivery.service;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.PackageNotFoundException;

import java.util.List;

public interface PackageTrackingService {
    List<TrackingEventDto> getTrackingHistory(String trackingNumber) throws PackageNotFoundException;
    void updateStatus(String trackingNumber, PackageStatusDto dto) throws PackageNotFoundException;
    void processTrackingEventFromQueue(TrackingEventDto eventDto);

    PackageDto createPackage(PackageDto dto);

    PackageDto findByTrackingNumber(String trackingNumber);
}