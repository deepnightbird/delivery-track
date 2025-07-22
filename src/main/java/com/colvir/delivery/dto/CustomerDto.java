package com.colvir.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CustomerDto {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;

    public CustomerDto(Long id, String name, String address, String phoneNumber){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
