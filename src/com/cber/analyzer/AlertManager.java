package com.cber.analyzer;

import java.util.ArrayList;
import java.util.List;

public class AlertManager {
    private final List<Alert> alertHistory = new ArrayList<>();

    public void sendAlert(Alert alert) {
        alertHistory.add(alert);
        System.out.println("\n " + alert.toString() + "\n");
    }

    public void showAlertHistory() {
        if (alertHistory.isEmpty()) {
            System.out.println("No alerts generated yet.");
        } else {
            System.out.println("=== Alert History ===");
            for (Alert alert : alertHistory) {
                System.out.println(alert);
            }
        }
    }
}
