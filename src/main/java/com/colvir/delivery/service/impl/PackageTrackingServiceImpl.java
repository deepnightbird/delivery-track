package com.colvir.delivery.service.impl;

import com.colvir.delivery.dto.CourierDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.CourierNotFoundException;
import com.colvir.delivery.exception.PackageNotFoundException;
import com.colvir.delivery.exception.PackageStatusNotFoundException;
import com.colvir.delivery.mapper.CourierMapper;
import com.colvir.delivery.mapper.PackageStatusMapper;
import com.colvir.delivery.mapper.TrackingEventMapper;
import com.colvir.delivery.mapper.PackageMapper;
import com.colvir.delivery.message.TrackingEventMessage;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import com.colvir.delivery.repository.CustomerRepository;
import com.colvir.delivery.repository.PackageRepository;
import com.colvir.delivery.repository.TrackingEventRepository;
import com.colvir.delivery.service.PackageTrackingService;
import com.colvir.delivery.service.TrackingNumberGenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageTrackingServiceImpl implements PackageTrackingService {

    private final PackageRepository packageRepository;
    private final TrackingEventRepository trackingEventRepository;
    private final TrackingEventMapper trackingEventMapper;
    private final PackageStatusMapper packageStatusMapper;
    private final PackageMapper packageMapper;
    private final CustomerRepository customerRepository;
    private final CourierMapper courierMapper;
    private final Random random = new Random();
    private final KafkaTemplate<String, TrackingEventMessage> kafkaTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<TrackingEventDto> getTrackingHistory(String trackingNumber)
            throws PackageNotFoundException {
        List <TrackingEvent> trackingEventList = trackingEventRepository.findByTrackingNumber(trackingNumber);
        List<TrackingEventDto> trackingEventDtoList = new ArrayList<>();
        for(TrackingEvent trackingEvent : trackingEventList){
            TrackingEventDto dto = trackingEventMapper.toDto(trackingEvent);
            trackingEventDtoList.add(dto);
        }
        return trackingEventDtoList;
    }

    @Transactional
    public PackageDto createPackage(PackageDto packageDto) {
        PackageDto newPackageDto = new PackageDto();
        customerRepository.findById(packageDto.getIdPackageSender()).ifPresent(
                packageSender -> newPackageDto.setIdPackageSender(packageSender.getId())
        );
        customerRepository.findById(packageDto.getIdPackageRecipient()).ifPresent(
                packageRecipient -> newPackageDto.setIdPackageRecipient(packageRecipient.getId())
        );
        newPackageDto.setTrackingNumber(TrackingNumberGenService.generateDomesticTracking());
        newPackageDto.setIdPackageStatus(packageRepository.getIdInitialStatus());
        newPackageDto.setDescription(packageDto.getDescription());
        newPackageDto.setWeight(packageDto.getWeight());
        newPackageDto.setCreatedAt(now());

        newPackageDto.setEstimatedDeliveryDate(newPackageDto.getCreatedAt().plusDays(random.nextInt(30)));
        packageRepository.save(packageMapper.toEntity(newPackageDto));
        packageRepository.findByTrackingNumber(newPackageDto.getTrackingNumber())
                    .ifPresent(pkg -> newPackageDto.setId(pkg.getId())
                );

        PackageStatusDto packageStatusDto = packageRepository.findStatusById(
                        newPackageDto.getIdPackageStatus()).map(packageStatusMapper::toDto)
                        .orElseThrow(() -> new PackageStatusNotFoundException(newPackageDto.getIdPackageStatus())
                );
        TrackingEventDto trackingEventDto = new TrackingEventDto();
        trackingEventDto.setCreatedAt(now());
        trackingEventDto.setLastUpdatedAt(now());
        trackingEventDto.setPackageDto(newPackageDto);
        trackingEventDto.setPackageStatusDto(packageStatusDto);
        trackingEventDto.setPackageId(newPackageDto.getId());
        trackingEventDto.setPackageStatusId(packageStatusDto.getId());
        this.AddEvent(trackingEventDto);

        return newPackageDto;
    }

    @Transactional
    public TrackingEventDto LinkToCourier(TrackingEventDto trackingEventDto) {
        PackageDto packageDto = packageRepository.findById(
                    trackingEventDto.getPackageId()).map(packageMapper::toDto)
                    .orElseThrow(() -> new PackageNotFoundException(trackingEventDto.getPackageId())
                );
        CourierDto courierDto = packageRepository.getCourierById(
                    trackingEventDto.getCourierId()).map(courierMapper::toDto)
                    .orElse(null);
        PackageStatusDto packageStatusDto = packageRepository.findStatusById(
                    trackingEventDto.getPackageStatusId()).map(packageStatusMapper::toDto)
                    .orElseThrow(() -> new PackageStatusNotFoundException(trackingEventDto.getPackageId())
                );
        trackingEventDto.setCreatedAt(now());
        trackingEventDto.setLastUpdatedAt(now());
        trackingEventDto.setPackageDto(packageDto);
        if (courierDto != null) {
            trackingEventDto.setCourierDto(courierDto);
        }
        trackingEventDto.setPackageStatusDto(packageStatusDto);
        trackingEventDto.setEventName("Linked");
        return this.AddEvent(trackingEventDto);
    }

    @Transactional
    public TrackingEventDto AddEvent(TrackingEventDto trackingEventDto) {
        PackageStatus packageStatus = packageRepository.findStatusById(trackingEventDto.getPackageStatusId())
                .orElseThrow(() -> new PackageStatusNotFoundException(trackingEventDto.getPackageStatusId())
                );
        TrackingEvent trackingEvent = trackingEventMapper.toEntity(trackingEventDto);
        trackingEvent.setStatus(packageStatus);
        trackingEvent.setCreatedAt(now());
        trackingEvent.setLastUpdatedAt(now());
        Package pkg = packageRepository.findById(trackingEventDto.getPackageId())
                .orElseThrow(() -> new PackageNotFoundException(trackingEventDto.getPackageId())
                );
        pkg.setStatus(packageStatus);
        trackingEvent.getPkg().setStatus(trackingEvent.getStatus());
        Boolean isTerminal = trackingEvent.getStatus().getIsTerminal();
        if (isTerminal) {
            pkg.setDeliveredAt(now());
        }
        packageRepository.save(pkg);
        trackingEventRepository.save(trackingEvent);
        if ( trackingEventDto.getCourierId() != null ) {
            trackingEventRepository.findByCourierAndPackageId(trackingEventDto.getCourierId(), trackingEventDto.getPackageId())
                    .ifPresent(trkEvent -> trackingEventDto.setCourierId(trkEvent.getCourier().getId())
                    );
        } else {
            trackingEventRepository.findLastByPackageId(trackingEventDto.getPackageId())
                    .ifPresent(trkEvent -> trackingEventDto.setId(trkEvent.getId())
                    );
        }

        return trackingEventDto;
    }

    @Transactional(readOnly = true)
    public Optional<PackageDto> findByTrackingNumber(String trackingNumber) {
        return packageRepository.findByTrackingNumber(trackingNumber).map(packageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional <PackageDto> getPackageById(Long Id) {
        return packageRepository.findById(Id).map(packageMapper::toDto);
    }
}