package com.colvir.delivery.repository;

import com.colvir.delivery.model.Package;
import com.colvir.delivery.model.PackageStatus;
import com.colvir.delivery.model.TrackingEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    // Найти все события для конкретной посылки (сортировка по времени)
    List<TrackingEvent> findByPkgOrderByEventTimeDesc(Package pkg);

    // Постраничный поиск событий для посылки
    Page<TrackingEvent> findByPkg(Package pkg, Pageable pageable);

    // Найти все события определенного статуса
    List<TrackingEvent> findByStatus(PackageStatus status);

    // Найти события в указанный период времени
    @Query("SELECT te FROM TrackingEvent te WHERE te.eventTime BETWEEN :start AND :end")
    List<TrackingEvent> findBetweenDates(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    // Найти последнее событие для посылки
    @Query("SELECT te FROM TrackingEvent te WHERE te.pkg = :pkg ORDER BY te.eventTime DESC LIMIT 1")
    TrackingEvent findLastEventForPackage(@Param("pkg") Package pkg);

    // Найти все события с определенным статусом для посылки
    @Query("SELECT te FROM TrackingEvent te WHERE te.pkg = :pkg AND te.status = :status")
    List<TrackingEvent> findByPackageAndStatus(@Param("pkg") Package pkg,
                                               @Param("status") PackageStatus status);

    // Получить количество событий определенного типа для посылки
    @Query("SELECT COUNT(te) FROM TrackingEvent te WHERE te.pkg = :pkg AND te.status = :status")
    long countByPackageAndStatus(@Param("pkg") Package pkg,
                                 @Param("status") PackageStatus status);

    // Найти все события в определенном местоположении
    List<TrackingEvent> findByLocationContainingIgnoreCase(String location);

    // Найти все события после указанной даты для списка посылок
    @Query("SELECT te FROM TrackingEvent te WHERE te.pkg IN :packages AND te.eventTime > :since")
    List<TrackingEvent> findRecentForPackages(@Param("packages") List<Package> packages,
                                              @Param("since") LocalDateTime since);

    // Удалить все события для посылки (используется при удалении посылки)
    void deleteByPkg(Package pkg);
}