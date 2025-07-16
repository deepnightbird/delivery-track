package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers",
        indexes = @Index(name = "idx_customer_email", columnList = "email", unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private String Address;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Package> sentPackages = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Package> receivedPackages = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    // Метод для добавления отправленной посылки
    public void addSentPackage(Package pkg) {
        sentPackages.add(pkg);
        pkg.setSender(this);
    }

    // Метод для добавления полученной посылки
    public void addReceivedPackage(Package pkg) {
        receivedPackages.add(pkg);
        pkg.setRecipient(this);
    }

    // Метод для проверки основных данных клиента
    public boolean hasRequiredInfo() {
        return name != null && !name.isBlank()
                && email != null && !email.isBlank()
                && phoneNumber != null && !phoneNumber.isBlank();
    }
}