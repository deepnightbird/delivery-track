package com.colvir.delivery.controller;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.service.PackageTrackingService;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {
    private final PackageTrackingService packageService;
    
    @GetMapping("/{trackingNumber}")
    public ResponseEntity<PackageDto> getByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(packageService.findByTrackingNumber(trackingNumber));
    }
    
    @PostMapping
    public ResponseEntity<PackageDto> createPackage(@Valid @RequestBody PackageDto dto) {
        return new ResponseEntity<>(packageService.createPackage(dto), HttpStatus.CREATED);
    }
    
    @GetMapping("/{trackingNumber}/tracking")
    public ResponseEntity<List<TrackingEventDto>> getTrackingHistory(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(packageService.getTrackingHistory(trackingNumber));
    }
    
    @PatchMapping("/{trackingNumber}/status")
    @PreAuthorize("hasRole('COURIER') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable String trackingNumber,
            @RequestBody PackageStatusDto dto) {
        packageService.updateStatus(trackingNumber, dto);
        return ResponseEntity.noContent().build();
    }
}
