package com.colvir.delivery.controller;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.service.PackageTrackingService;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.dto.TrackingEventDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
@Validated
public class PackageController {
    private final PackageTrackingService packageTrackingService;
    
    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<PackageDto> getStatusByTrackingNumber(@PathVariable @NotNull String trackingNumber) {
        return packageTrackingService.findByTrackingNumber(trackingNumber).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PackageDto> createPackage(@Valid @RequestBody PackageDto dto) {
        //return new ResponseEntity<>(packageTrackingService.createPackage(dto), HttpStatus.CREATED);
        return null;
    }
    
    @GetMapping("/track/{trackingNumber}/history")
    public ResponseEntity<List<TrackingEventDto>> getTrackingHistory(@PathVariable String trackingNumber) {
        //return ResponseEntity.ok(packageTrackingService.getTrackingHistory(trackingNumber));
        return null;
    }
    
    @PatchMapping("/track/{trackingNumber}")
    //@PreAuthorize("hasRole('COURIER') or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> updateStatus(
            @PathVariable String trackingNumber,
            @RequestBody PackageStatusDto dto) {
        // packageTrackingService.updateStatus(trackingNumber, dto);
        return ResponseEntity.noContent().build();
    }
}
