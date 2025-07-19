package com.colvir.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tracking_events")
@Data
@NoArgsConstructor
public class TrackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tracking_events_id_seq")
    @SequenceGenerator(name = "tracking_events_id_seq", sequenceName = "tracking_events_id_seq", allocationSize = 1)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_package")
    private Package pkg;

    @ManyToOne
    @JoinColumn(name = "id_courier")
    private Courier courier;
    
    private String location;
    private String eventName;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;

    public boolean shouldSendNotification(){
        return pkg.getStatus().isTerminal(); // || pkg.getStatus().isInitial();
    }


}
