package com.colvir.delivery.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
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
}