package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_events")
@Data
@NoArgsConstructor
public class TrackingEvent {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package pkg;
    
    private String location;
    private String description;
    
    @CreationTimestamp
    private LocalDateTime eventTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
