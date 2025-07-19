
package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
public class Package {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String trackingNumber;
    private String description;
    private double weight;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private PackageStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Customer sender;
    
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Customer recipient;
    
    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL)
    private List<TrackingEvent> trackingEvents = new ArrayList<>();
    
    @CreationTimestamp
    private LocalDateTime createdAt;

}
