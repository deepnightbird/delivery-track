package com.colvir.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PackageStatusNotFoundException extends RuntimeException {
    private final Long id;

    public PackageStatusNotFoundException(Long id) {
        super(String.format("Package status with tracking id '%s' not found", id));
        this.id = id;
    }

    public PackageStatusNotFoundException(Long id, Throwable cause) {
        super(String.format("Package status with tracking id '%s' not found", id), cause);
        this.id = id;
    }
}
