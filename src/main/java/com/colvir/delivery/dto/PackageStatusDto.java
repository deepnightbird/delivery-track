package com.colvir.delivery.dto;

import com.colvir.delivery.model.PackageStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackageStatusDto{
    private Long id;
    private String name;
}