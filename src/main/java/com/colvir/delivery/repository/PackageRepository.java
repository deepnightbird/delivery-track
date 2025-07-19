package com.colvir.delivery.repository;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    Optional<Package> findByTrackingNumber(String trackingNumber);

    List<Package> findByStatus(PackageStatus status);

    @Query("""
        SELECT p FROM packages p WHERE p.sender.id = :senderId
    """)
    List<Package> findBySenderId(@Param("senderId") Long senderId);

    @Query("""
        SELECT p FROM packages p WHERE p.recipient.id = :recipientId
    """)
    List<Package> findByRecipientId(@Param("recipientId") Long recipientId);

    List<Package> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
        SELECT p FROM packages p WHERE p.status = :status AND p.weight > :minWeight
    """)
    List<Package> findByStatusAndWeightGreaterThan(
            @Param("status") PackageStatus status,
            @Param("minWeight") double minWeight);

    @Query(value = """
        UPDATE packages p SET p.id_package_status = (SELECT s.id FROM package_stauts s WHERE s.name = :statusName) WHERE p.id = :id
    """, nativeQuery = true)
    @Modifying
    void updateStatusName(@Param("id") Long id, @Param("statusName") String statusName);

    @Query("""
        SELECT p FROM packages p WHERE p.courier.id = :courierId AND p.status IN :statuses
    """)
    List<Package> findByCourierIdAndStatusIn(
            @Param("courierId") Long courierId,
            @Param("statuses") List<PackageStatus> statuses);

    boolean existsByTrackingNumber(String trackingNumber);

    @Query("""
        SELECT p FROM packages p WHERE p.status != com.delivery.model.PackageStatus.DELIVERED
        AND p.estimatedDeliveryDate < :currentDate
    """)
    List<Package> findOverduePackages(@Param("currentDate") LocalDateTime currentDate);

    void save(PackageDto packageDto);

}