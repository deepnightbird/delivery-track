package com.colvir.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventDto{

    private Long id;
    private String location;
    private String eventName;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private Long id_courier;

}