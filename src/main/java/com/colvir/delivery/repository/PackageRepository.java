package com.colvir.delivery.repository;

import com.colvir.delivery.model.Courier;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    @Query(value = """
        SELECT p.id, p.tracking_number, p.description, p.weight,
               p.id_package_status, p.id_customer_sender, p.id_customer_recipient,
               p.created_at, p.estimated_delivery_date, p.delivered_at
        FROM packages p
        WHERE p.tracking_number = :trackingNumber
    """, nativeQuery = true)
    Optional<Package> findByTrackingNumber(@Param("trackingNumber") String trackingNumber);

    @Query(value = """
        SELECT p.id
        FROM package_status p
        WHERE p.is_initial = true
    """, nativeQuery = true)
    Long getIdInitialStatus();

    @Query(value = """
        SELECT c.id, c.name
        FROM couriers c
        WHERE c.id = :id
    """, nativeQuery = true)
    Optional<Courier> getCourierById(@Param("id") Long id);

    @Query(value = """
        SELECT s.id, s.name, s.is_initial, s.is_terminal
        FROM package_status s
        WHERE s.id = :id
    """, nativeQuery = true)
    Optional<PackageStatus> findStatusById(@Param("id") Long id);

    @Query(value = """
        SELECT p.id, p.tracking_number, p.description, p.weight, p.id_package_status, p.id_customer_sender, p.id_customer_recipient, p.created_at, p.estimated_delivery_date, p.delivered_at
        FROM packages p
        WHERE p.id = :id
    """, nativeQuery = true)
    Optional<Package> findById(@Param("id") Long id);
}