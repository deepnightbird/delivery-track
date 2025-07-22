package com.colvir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.colvir.delivery.model"})
@EnableJpaRepositories(basePackages = {"com.colvir.delivery.repository"})
@ComponentScan(basePackages = {"com.colvir.delivery"})
public class DeliveryApplication {
    public static void main(String[] args) {

        SpringApplication.run(DeliveryApplication.class, args);
    }
}