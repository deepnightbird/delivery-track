package com.colvir.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PackageNotFoundException extends RuntimeException {

    private final Long id;

    public PackageNotFoundException(Long id) {
        super(String.format("Package with tracking id '%s' not found", id));
        this.id = id;
    }

    public PackageNotFoundException(Long id, Throwable cause) {
        super(String.format("Package with tracking id '%s' not found", id), cause);
        this.id = id;
    }
}