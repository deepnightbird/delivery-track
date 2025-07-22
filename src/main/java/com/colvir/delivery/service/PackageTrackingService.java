package com.colvir.delivery.service;

import com.colvir.delivery.dto.CourierDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.PackageNotFoundException;
import com.colvir.delivery.model.TrackingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public interface PackageTrackingService {
    List<TrackingEventDto> getTrackingHistory(String trackingNumber)
            throws PackageNotFoundException;

    @Transactional
    PackageDto createPackage(PackageDto packageDto);

    @Transactional(readOnly = true)
    Optional <PackageDto> findByTrackingNumber(String trackingNumber);

    @Transactional(readOnly = true)
    Optional <PackageDto> getPackageById(Long Id);

    @Transactional
    TrackingEventDto LinkToCourier(TrackingEventDto trackingEventDto);

    @Transactional
    TrackingEventDto AddEvent(TrackingEventDto trackingEventDto);
}