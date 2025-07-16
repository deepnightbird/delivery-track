package com.colvir.delivery.repository;

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

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    // Найти посылку по tracking number
    Optional<Package> findByTrackingNumber(String trackingNumber);

    // Найти все посылки с определенным статусом
    List<Package> findByStatus(PackageStatus status);

    // Найти посылки по ID отправителя
    @Query("SELECT p FROM Package p WHERE p.sender.id = :senderId")
    List<Package> findBySenderId(@Param("senderId") Long senderId);

    // Найти посылки по ID получателя
    @Query("SELECT p FROM Package p WHERE p.recipient.id = :recipientId")
    List<Package> findByRecipientId(@Param("recipientId") Long recipientId);

    // Найти посылки, созданные в определенный период
    List<Package> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Найти посылки по статусу и весу больше указанного
    @Query("SELECT p FROM Package p WHERE p.status = :status AND p.weight > :minWeight")
    List<Package> findByStatusAndWeightGreaterThan(
            @Param("status") PackageStatus status,
            @Param("minWeight") double minWeight);

    // Обновить статус посылки
    @Modifying
    @Query("UPDATE Package p SET p.status = :status WHERE p.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") PackageStatus status);

    // Найти посылки по ID курьера
    @Query("SELECT p FROM Package p WHERE p.courier.id = :courierId AND p.status IN :statuses")
    List<Package> findByCourierIdAndStatusIn(
            @Param("courierId") Long courierId,
            @Param("statuses") List<PackageStatus> statuses);

    // Проверить существование посылки по tracking number
    boolean existsByTrackingNumber(String trackingNumber);

    // Найти просроченные посылки (статус не DELIVERED после estimatedDeliveryDate)
    @Query("SELECT p FROM Package p WHERE p.status != com.delivery.model.PackageStatus.DELIVERED " +
            "AND p.estimatedDeliveryDate < :currentDate")
    List<Package> findOverduePackages(@Param("currentDate") LocalDateTime currentDate);
}