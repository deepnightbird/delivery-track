package com.colvir.delivery.dto;

import com.colvir.delivery.model.TrackingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Tracking event information")
public record TrackingEventDto(
        @Schema(description = "Unique tracking number of the package",
                example = "TRK123456789",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String trackingNumber,

        @Schema(description = "Status of the package at this event",
                example = "IN_TRANSIT",
                requiredMode = Schema.RequiredMode.REQUIRED)
        TrackingStatus status,

        @Schema(description = "Location where the event occurred",
                example = "Moscow Distribution Center")
        String location,

        @Schema(description = "Additional details about the event",
                example = "Package scanned at sorting facility")
        String description,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Timestamp of the event",
                example = "2023-05-15 14:30:00",
                requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime eventTime
) {
    // Optional: Builder method for fluent creation
    public static TrackingEventDtoBuilder builder() {
        return new TrackingEventDtoBuilder();
    }

    public TrackingStatus getStatus() {
        return status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getEventTime() {
        return eventTime.toString();
    }

    // Builder class
    public static class TrackingEventDtoBuilder {
        private String trackingNumber;
        private TrackingStatus status;
        private String location;
        private String description;
        private LocalDateTime eventTime;

        public TrackingEventDtoBuilder trackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public TrackingEventDtoBuilder status(TrackingStatus status) {
            this.status = status;
            return this;
        }

        public TrackingEventDtoBuilder location(String location) {
            this.location = location;
            return this;
        }

        public TrackingEventDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TrackingEventDtoBuilder eventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public TrackingEventDto build() {
            return new TrackingEventDto(trackingNumber, status, location, description, eventTime);
        }
    }
}