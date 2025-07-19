package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "couriers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "couriers_id_seq")
    @SequenceGenerator(name = "couriers_id_seq", sequenceName = "couriers_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Package> deliveries = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

}
