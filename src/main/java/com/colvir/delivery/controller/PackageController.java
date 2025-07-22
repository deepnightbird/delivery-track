package com.colvir.delivery.controller;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.model.TrackingEvent;
import com.colvir.delivery.service.PackageTrackingService;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
@RequiredArgsConstructor
@Validated
public class PackageController {
    private final PackageTrackingService packageTrackingService;
    
    @GetMapping("/{trackingNumber}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PackageDto> getStatusByTrackingNumber(@PathVariable String trackingNumber) {
        return packageTrackingService.findByTrackingNumber(trackingNumber).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PackageDto> createPackage(@Valid @RequestBody PackageDto packageDto) {
        return new ResponseEntity<>(packageTrackingService.createPackage(packageDto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{trackingNumber}/history")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TrackingEventDto>> getTrackingHistory(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(packageTrackingService.getTrackingHistory(trackingNumber));
    }
    
    @PostMapping("/link")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TrackingEventDto> linkToCourier(@Valid @RequestBody TrackingEventDto trackingEventDto) {
        return new ResponseEntity<>(packageTrackingService.LinkToCourier(trackingEventDto), HttpStatus.CREATED);
    }

    @GetMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TrackingEventDto> updateStatus(@Valid @RequestBody TrackingEventDto trackingEventDto) {
        return new ResponseEntity<>(packageTrackingService.AddEvent(trackingEventDto), HttpStatus.OK);
    }
}
