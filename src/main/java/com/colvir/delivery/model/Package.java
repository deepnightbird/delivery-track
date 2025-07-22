
package com.colvir.delivery.model;

import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.dto.PackageStatusDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packages_id_seq")
    @SequenceGenerator(name = "packages_id_seq", sequenceName = "packages_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    private String description;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_package_status")
    private PackageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer_sender")
    private Customer sender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer_recipient")
    private Customer recipient;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

}
