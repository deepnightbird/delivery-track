package com.colvir.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PackageStatusDto{
    private Long id;
    private String name;
    private Boolean isInitial;
    private Boolean isTerminal;

    public boolean isInitialStatus() {
        return this.isInitial;
    }

    public boolean isTerminalStatus() {
        return this.isTerminal;
    }

}