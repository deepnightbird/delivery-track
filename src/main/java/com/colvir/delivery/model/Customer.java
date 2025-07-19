package com.colvir.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_seq")
    @SequenceGenerator(name = "customers_id_seq", sequenceName = "customers_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String phoneNumber;

    private String address;

    // потом удалить !!!
    /*@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Package> sentPackages = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Package> receivedPackages = new ArrayList<>();

    public void addSentPackage(Package pkg) {
        sentPackages.add(pkg);
        pkg.setSender(this);
    }

    public void addReceivedPackage(Package pkg) {
        receivedPackages.add(pkg);
        pkg.setRecipient(this);
    }

    public boolean hasRequiredInfo() {
        return name != null && !name.isBlank()
                && phoneNumber != null && !phoneNumber.isBlank();
    }*/
}