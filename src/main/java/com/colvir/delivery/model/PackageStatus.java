package com.colvir.delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "package_status")
@Data
@NoArgsConstructor
public class PackageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "package_status_id_seq")
    @SequenceGenerator(name = "package_status_id_seq", sequenceName = "package_status_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Column
    private boolean isInitial;

    @Column
    private boolean isTerminal;

    public String toString() {
        return this.name;
    }
}