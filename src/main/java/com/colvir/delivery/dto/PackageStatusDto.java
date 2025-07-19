package com.colvir.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PackageStatusDto{
    private Long id;
    private String name;
    private boolean isInitial;
    private boolean isTerminal;

    public boolean isInitialStatus() {
        // this == DELIVERED || this == RETURNED || this == FAILED_DELIVERY;
        return this.isInitial;
    }

    public boolean isTerminalStatus() {
        // this == DELIVERED || this == RETURNED || this == FAILED_DELIVERY;
        return this.isTerminal;
    }

    /*public PackageStatusDto(Long id, String name, boolean isInitial, boolean isTerminal) {
        this.id = id;
        this.name = name;
        this.isInitial = isInitial;
        this.isTerminal = isTerminal;
    }*/

}