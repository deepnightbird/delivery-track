package com.colvir.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

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

    // удалить !!!
    /* public boolean shouldSendNotification(){
        return pkg.getStatus().isTerminal(); // || pkg.getStatus().isInitial();
    }*/


}
