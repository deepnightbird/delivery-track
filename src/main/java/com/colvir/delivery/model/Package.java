
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
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable( name = "package_status", joinColumns =  @JoinColumn(name = "id_package_status"))
    private PackageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer_sender")
    private Customer sender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer_recipient")
    private Customer recipient;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
