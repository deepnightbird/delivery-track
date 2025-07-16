package com.colvir.delivery.dto;

import com.colvir.delivery.model.TrackingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateDto(
        @NotNull(message = "Status cannot be null")
        @Schema(description = "New tracking status of the package",
                example = "IN_TRANSIT",
                requiredMode = Schema.RequiredMode.REQUIRED)
        TrackingStatus status,

        @NotBlank(message = "Location cannot be blank")
        @Schema(description = "Current location of the package",
                example = "Moscow Distribution Center",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String location,

        @Schema(description = "Additional description or comments",
                example = "Package is on the way to recipient")
        String description
) {
    // Валидация бизнес-правил
    public StatusUpdateDto {
        if (status == TrackingStatus.DELIVERED && (location == null || location.isBlank())) {
            throw new IllegalArgumentException("Location must be specified for DELIVERED status");
        }
    }

    public TrackingStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}