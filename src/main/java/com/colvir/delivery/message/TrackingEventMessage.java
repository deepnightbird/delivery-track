package com.colvir.delivery.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventMessage {
    private String trackingNumber;
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;

    // Дополнительные метаданные
    private String courierId;
    private String deviceId;
    private Double latitude;
    private Double longitude;

    public String getNotificationMessage() {
        return String.format("Посылка %s: %s в %s (%s)",
                trackingNumber,
                location,
                eventTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

}