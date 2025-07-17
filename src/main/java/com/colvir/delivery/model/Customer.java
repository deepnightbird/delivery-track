package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String Address;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
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
    }
}