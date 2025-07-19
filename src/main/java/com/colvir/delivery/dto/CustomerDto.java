package com.colvir.delivery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CustomerDto {
    private Long id;
    private String name;
    private String address;
    private String phone;

    public CustomerDto(Long id, String name, String address, String phone){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
