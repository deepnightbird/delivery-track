package com.colvir.delivery.dto;

import com.colvir.delivery.model.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageStatusDto{
    private Long id;
    private String name;
    private boolean isTerminal;
    private boolean isInitial;

    public boolean isInitialStatus() {
        // this == DELIVERED || this == RETURNED || this == FAILED_DELIVERY;
        return this.isInitial;
    }

    public boolean isTerminalStatus() {
        // this == DELIVERED || this == RETURNED || this == FAILED_DELIVERY;
        return this.isTerminal;
    }

}