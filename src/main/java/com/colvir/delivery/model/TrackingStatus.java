package com.colvir.delivery.model;

public enum TrackingStatus {
    CREATED,
    PROCESSING,
    IN_TRANSIT,
    AT_DISTRIBUTION_CENTER,
    OUT_FOR_DELIVERY,
    DELIVERED,
    RETURNED,
    FAILED_DELIVERY;

    public boolean isTerminalStatus() {
        return this == DELIVERED || this == RETURNED || this == FAILED_DELIVERY;
    }

    public PackageStatus toPackageStatus() {
        return PackageStatus.valueOf(this.name());
    }
}