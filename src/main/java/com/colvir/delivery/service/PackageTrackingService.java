package com.colvir.delivery.service;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.PackageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PackageTrackingService {
    List<TrackingEventDto> getTrackingHistory(String trackingNumber)
            throws PackageNotFoundException;
    void updateStatus(String trackingNumber, PackageStatusDto dto)
            throws PackageNotFoundException;
    void processTrackingEventFromQueue(TrackingEventDto eventDto);

    PackageDto createPackage(PackageDto dto);

    Optional <PackageDto> findByTrackingNumber(String trackingNumber);
}