package com.colvir.delivery.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PackageNotFoundException extends RuntimeException {

    private final String trackingNumber;

    public PackageNotFoundException(String trackingNumber) {
        super(String.format("Package with tracking number '%s' not found", trackingNumber));
        this.trackingNumber = trackingNumber;
    }

    public PackageNotFoundException(String trackingNumber, Throwable cause) {
        super(String.format("Package with tracking number '%s' not found", trackingNumber), cause);
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this; // Оптимизация - не заполняем stack trace для известных ошибок
    }
}