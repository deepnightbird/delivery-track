
package com.colvir.delivery.model;

import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.dto.PackageStatusDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
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
    
    private String trackingNumber;
    private String description;
    private double weight;
    @ManyToOne
    @JoinColumn(name = "id_status")
    private PackageStatus status;

    @ManyToOne
    @JoinColumn(name = "id_sender_id")
    private Customer sender;
    
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Customer recipient;

    // удалить !!!!
    /*@OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL)
    private List<TrackingEvent> trackingEvents = new ArrayList<>();*/
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Package(Long id, String trackingNumber, String description, float weight, PackageStatusDto statusDto, CustomerDto senderDto, CustomerDto recepientDto) {
        this.id = id;
        this.trackingNumber = trackingNumber;
        this.description = description;
        this.weight = weight;
        // здесь надо доделать !!!!!
        /*this.status = statusDto;
        this.sender = senderDto;
        this.recipient = recepientDto;*/
    }

}
