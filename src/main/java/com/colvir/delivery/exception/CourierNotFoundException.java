package com.colvir.delivery.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourierNotFoundException extends RuntimeException {

    private final Long courierId;

    public CourierNotFoundException(Long courierId) {
        super(String.format("Courier with id '%d' not found", courierId));
        this.courierId = courierId;
    }

    public CourierNotFoundException(Long courierId, Throwable cause) {
        super(String.format("Courier with id '%d' not found", courierId), cause);
        this.courierId = courierId;
    }
}
