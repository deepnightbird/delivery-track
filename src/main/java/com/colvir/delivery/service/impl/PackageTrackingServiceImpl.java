package com.colvir.delivery.service.impl;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.exception.PackageNotFoundException;
import com.colvir.delivery.mapper.PackageStatusMapper;
import com.colvir.delivery.mapper.TrackingEventMapper;
import com.colvir.delivery.mapper.PackageMapper;
import com.colvir.delivery.message.TrackingEventMessage;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.TrackingEvent;
import com.colvir.delivery.repository.PackageRepository;
import com.colvir.delivery.repository.TrackingEventRepository;
import com.colvir.delivery.service.PackageTrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageTrackingServiceImpl implements PackageTrackingService {

    private final PackageRepository packageRepository;
    private final TrackingEventRepository trackingEventRepository;
    private final TrackingEventMapper trackingEventMapper;
    private final PackageStatusMapper packageStatusMapper;
    private final PackageMapper packageMapper;
    private final KafkaTemplate<String, TrackingEventMessage> kafkaTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<TrackingEventDto> getTrackingHistory(String trackingNumber) throws PackageNotFoundException {
        Package pkg = packageRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new PackageNotFoundException(trackingNumber));

        return trackingEventRepository.findByPkgOrderByEventTimeDesc(pkg).stream()
                .map(trackingEventMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void updateStatus(String trackingNumber, PackageStatusDto dto) throws PackageNotFoundException {
        Package pkg = packageRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new PackageNotFoundException(trackingNumber));

        TrackingEvent event = createTrackingEvent(pkg, dto);
        trackingEventRepository.save(event);

        if (event.shouldSendNotification()) {
            sendTrackingEventToKafka(trackingNumber, event);
        }

        updatePackageStatusIfNeeded(pkg, dto);
    }

    @Override
    @Transactional
    public void processTrackingEventFromQueue(TrackingEventDto eventDto) {
        try {
            Optional<Package> pkgOpt = packageRepository.findByTrackingNumber(eventDto.getTrackingNumber());
            if (pkgOpt.isEmpty()) {
                log.warn("Package not found for tracking number: {}", eventDto.getTrackingNumber());
                return;
            }

            Package pkg = pkgOpt.get();
            TrackingEvent event = trackingEventMapper.toEntity(eventDto);
            event.setPkg(pkg);
            trackingEventRepository.save(event);

            log.info("Processed tracking event from queue for package: {}", eventDto.getTrackingNumber());
        } catch (Exception e) {
            log.error("Error processing tracking event from queue: {}", e.getMessage(), e);
        }
    }

    @Override
    public PackageDto createPackage(PackageDto dto) {
        return new PackageDto();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PackageDto> findByTrackingNumber(String trackingNumber) {
        return packageRepository.findByTrackingNumber(trackingNumber).map(packageMapper::toDto);
    }

    private TrackingEvent createTrackingEvent(Package pkg, PackageStatusDto dto) {
        TrackingEvent event = new TrackingEvent();
        event.setPkg(pkg);
       /* event.setStatus(dto.getStatus());
        event.setLocation(dto.getLocation());
        event.setDescription(dto.getDescription());*/
        return event;
    }

    private void sendTrackingEventToKafka(String trackingNumber, TrackingEvent event) {
        TrackingEventMessage message = TrackingEventMessage.builder()
                .trackingNumber(trackingNumber)
                //.status(event.getStatus())
                .location(event.getLocation())
                .eventTime(LocalDateTime.now())
                .build();

        CompletableFuture<SendResult<String, TrackingEventMessage>> future = kafkaTemplate.send("tracking-events", message);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Error while sending event! Check Kafka broker", exception);
            } else {
                log.info("Event sent {} successfully with offset {}", event, result.getRecordMetadata().offset());
            }
        });
        /*.
                result -> log.info("Successfully sent tracking event to Kafka for package: {}", trackingNumber),
                ex -> log.error("Failed to send tracking event to Kafka for package: {}", trackingNumber, ex)
        );*/
    }

    private void updatePackageStatusIfNeeded(Package pkg, PackageStatusDto status) {
        if (status.isTerminalStatus()) {
            pkg.setStatus(packageStatusMapper.toEntity(status));
            packageRepository.save(pkg);
            log.info("Updated package status to {} for package: {}", status, pkg.getTrackingNumber());
        }
    }

    /*private boolean shouldSendNotification(TrackingStatus status) {
        return status != TrackingStatus.CREATED;
    }*/
}