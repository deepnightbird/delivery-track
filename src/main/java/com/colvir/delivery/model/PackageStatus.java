package com.colvir.delivery.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Статусы жизненного цикла посылки в системе доставки
 */
public enum PackageStatus {

    @JsonProperty("CREATED")
    CREATED("Создана", "Посылка создана в системе"),

    @JsonProperty("PROCESSING")
    PROCESSING("В обработке", "Посылка готовится к отправке"),

    @JsonProperty("SHIPPED")
    SHIPPED("Отправлена", "Посылка передана в службу доставки"),

    @JsonProperty("IN_TRANSIT")
    IN_TRANSIT("В пути", "Посылка находится в процессе доставки"),

    @JsonProperty("AT_WAREHOUSE")
    AT_WAREHOUSE("На складе", "Посылка находится на складе доставки"),

    @JsonProperty("OUT_FOR_DELIVERY")
    OUT_FOR_DELIVERY("У курьера", "Курьер везет посылку получателю"),

    @JsonProperty("DELIVERED")
    DELIVERED("Доставлена", "Посылка успешно доставлена"),

    @JsonProperty("RETURNED")
    RETURNED("Возвращена", "Посылка возвращена отправителю"),

    @JsonProperty("FAILED")
    FAILED("Ошибка доставки", "Не удалось доставить посылку");

    private final String russianName;
    private final String description;

    PackageStatus(String russianName, String description) {
        this.russianName = russianName;
        this.description = description;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Проверяет, является ли статус конечным (посылка больше не в доставке)
     */
    public boolean isTerminal() {
        return this == DELIVERED || this == RETURNED || this == FAILED;
    }

    /**
     * Проверяет, можно ли обновить статус на указанный
     */
    public boolean canTransitionTo(PackageStatus newStatus) {
        // Логика допустимых переходов между статусами
        return switch (this) {
            case CREATED -> newStatus == PROCESSING || newStatus == FAILED;
            case PROCESSING -> newStatus == SHIPPED || newStatus == RETURNED;
            case SHIPPED -> newStatus == IN_TRANSIT;
            case IN_TRANSIT -> newStatus == AT_WAREHOUSE || newStatus == OUT_FOR_DELIVERY;
            case AT_WAREHOUSE -> newStatus == OUT_FOR_DELIVERY || newStatus == RETURNED;
            case OUT_FOR_DELIVERY -> newStatus == DELIVERED || newStatus == FAILED;
            case DELIVERED, RETURNED, FAILED -> false;
        };
    }

    @Override
    public String toString() {
        return this.name() + " (" + russianName + ")";
    }
}