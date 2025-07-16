package com.colvir.delivery.dto;

import com.colvir.delivery.model.PackageStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Информация о посылке")
public class PackageDto {

    @Schema(description = "ID посылки", example = "123")
    private Long id;

    @Schema(description = "Трек-номер посылки", example = "TRK123456789", required = true)
    private String trackingNumber;

    @Schema(description = "Описание содержимого", example = "Электронные компоненты")
    private String description;

    @Schema(description = "Вес посылки (кг)", example = "2.5")
    private Double weight;

    @Schema(description = "Текущий статус посылки", example = "IN_TRANSIT", required = true)
    private PackageStatus status;

    @Schema(description = "Дата создания посылки")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "Предполагаемая дата доставки")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryDate;

    @Schema(description = "Информация об отправителе")
    private CustomerShortDto sender;

    @Schema(description = "Информация о получателе")
    private CustomerShortDto recipient;

    @Schema(description = "Информация о курьере")
    private CourierShortDto courier;

    @Schema(description = "История событий трекинга")
    private List<TrackingEventShortDto> trackingHistory;

    @Schema(description = "Текущее местоположение посылки", example = "Москва, сортировочный центр")
    private String currentLocation;

    @Schema(description = "Флаг хрупкости", example = "false")
    private Boolean isFragile;

    @Schema(description = "Размеры посылки (ДxШxВ см)", example = "30x20x15")
    private String dimensions;

    @Schema(description = "Стоимость доставки", example = "500.00")
    private Double deliveryCost;

    // Метод для проверки заполненности обязательных полей
    public boolean hasRequiredFields() {
        return trackingNumber != null && !trackingNumber.isEmpty()
                && status != null && weight != null && weight > 0;
    }

    // Вложенный DTO для краткой информации о клиенте
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerShortDto {
        private Long id;
        private String name;
        private String phone;
    }

    // Вложенный DTO для краткой информации о курьере
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourierShortDto {
        private Long id;
        private String name;
        private String vehicleNumber;
    }

    // Вложенный DTO для краткой информации о событии трекинга
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrackingEventShortDto {
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventTime;
        private String status;
        private String location;
    }
}