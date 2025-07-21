package com.colvir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.colvir.delivery.mapper"})
public class DeliveryApplication {
    public static void main(String[] args) {

        SpringApplication.run(DeliveryApplication.class, args);
    }
}