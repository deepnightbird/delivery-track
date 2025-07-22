package com.colvir.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KafkaEventDto {

    private static Long COUNTER = 1L;

    private Long id;
    private String name;
    private String description;
    private String type;

    public KafkaEventDto(String name) {
        this.id = COUNTER++;
        this.name = name;
        this.description = "More details";
        this.type = "Event";
    }
}
