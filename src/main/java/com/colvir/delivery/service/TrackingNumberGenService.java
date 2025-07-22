package com.colvir.delivery.service;

import java.util.Random;
import java.util.UUID;

public class TrackingNumberGenService {

    public static String generateInternationalTracking() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 2 буквы в начале
        sb.append((char)('A' + random.nextInt(26)));
        sb.append((char)('A' + random.nextInt(26)));

        // 9 цифр
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }

        // 2 буквы в конце
        sb.append((char)('A' + random.nextInt(26)));
        sb.append((char)('A' + random.nextInt(26)));

        return sb.toString();
    }

    public static String generateDomesticTracking() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static String generateUUIDBasedTracking() {
        return "TN" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

}
