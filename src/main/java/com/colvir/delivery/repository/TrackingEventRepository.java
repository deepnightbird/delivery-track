package com.colvir.delivery.repository;

import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    List<TrackingEvent> findByPkgOrderByEventTimeDesc(Package pkg);

    Page<TrackingEvent> findByPkg(Package pkg, Pageable pageable);

    List<TrackingEvent> findByStatus(PackageStatus status);

    @Query("""
        SELECT te 
        FROM tracking_event te 
        WHERE te.created_at BETWEEN :start AND :end
        ORDER BY te.created_at DESC 
    """)
    List<TrackingEvent> findBetweenDates(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("""
        SELECT te 
        FROM tracking_event te, packages p 
        WHERE te.id_package = p.id and p.trackingNumber = :trackingNumber 
        ORDER BY te.eventTime
        DESC LIMIT 1
    """)
    TrackingEvent findLastEventForPackage(@Param("trackingNumber") String trackingNumber);

    @Modifying
    void deleteByPkg(Package pkg);
}