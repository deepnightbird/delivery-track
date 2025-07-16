package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "couriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourierStatus status;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String currentLocation;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Package> deliveries = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    // Методы для управления доставками
    public void addDelivery(Package pkg) {
        deliveries.add(pkg);
        pkg.setCourier(this);
    }

    public void removeDelivery(Package pkg) {
        deliveries.remove(pkg);
        pkg.setCourier(null);
    }

    // Метод для получения полного имени
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Метод проверки доступности курьера
    public boolean isAvailable() {
        return status == CourierStatus.AVAILABLE;
    }

    // Enum для статусов курьера
    public enum CourierStatus {
        AVAILABLE,
        ON_DELIVERY,
        ON_BREAK,
        OFF_DUTY,
        SUSPENDED
    }
}
