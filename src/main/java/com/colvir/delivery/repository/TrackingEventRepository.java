package com.colvir.delivery.repository;

import com.colvir.delivery.dto.CourierDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.TrackingEventDto;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import io.micrometer.common.KeyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    @Query(value = """
        SELECT t.id, t.event_name, t.id_package, t.id_courier, t.id_package_status, t.location, t.created_at, t.last_updated_at
        FROM tracking_events t
        WHERE t.id_courier = :courierId
            AND t.id_package = :packageId
        ORDER BY t.id DESC
        LIMIT 1
    """, nativeQuery = true)
    Optional<TrackingEvent> findByCourierAndPackageId(Long courierId, Long packageId);

    @Query(value = """
        SELECT t.id, t.event_name, t.id_package, t.id_courier, t.id_package_status, t.location, t.created_at, t.last_updated_at
        FROM tracking_events t
        WHERE t.id_package = :packageId
        ORDER BY t.id DESC
        LIMIT 1
    """, nativeQuery = true)
    Optional <TrackingEvent> findLastByPackageId(Long packageId);

    @Query(value =  """
        SELECT te.id, te.event_name, te.id_package, te.id_package_status, te.id_courier, te.location, te.created_at, te.last_updated_at
        FROM tracking_events te
        WHERE te.id_package = (select p.id from packages p where p.tracking_number=:trackingNumber)
        ORDER BY te.id ASC
    """, nativeQuery = true)
    List <TrackingEvent> findByTrackingNumber(String trackingNumber);
}