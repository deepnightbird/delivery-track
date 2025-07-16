package com.colvir.delivery.message;

import com.colvir.delivery.model.TrackingStatus;
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
    private TrackingStatus status;
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;

    // Дополнительные метаданные
    private String courierId;
    private String deviceId;
    private Double latitude;
    private Double longitude;

    /**
     * Проверяет, является ли событие значимым для уведомления клиента
     *
    public boolean isNotificationRequired() {
        return status.isMilestone() || status == TrackingStatus.FAILED_DELIVERY;
    }*/

    /**
     * Создает краткое описание события для уведомления
     */
    public String getNotificationMessage() {
        return String.format("Посылка %s: %s в %s (%s)",
                trackingNumber,
                status,
                location,
                eventTime.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

    /**
     * Проверяет наличие геоданных
     */
    public boolean hasGeoData() {
        return latitude != null && longitude != null;
    }
}