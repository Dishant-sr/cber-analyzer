package com.cber.analyzer;

import java.util.HashMap;

public class ThreatAnalyzer {
    private final HashMap<String, Integer> failedAttempts = new HashMap<>();
    private static final int MAX_FAILED_ATTEMPTS = 5;

    public boolean isSuspicious(LogEntry entry) {
        String message = entry.getMessage().toLowerCase();

        if (message.contains("failed password") || message.contains("authentication failure")) {
            String userKey = extractUserKey(message);
            failedAttempts.put(userKey, failedAttempts.getOrDefault(userKey, 0) + 1);

            if (failedAttempts.get(userKey) > MAX_FAILED_ATTEMPTS) {
                return true;
            }
        }
        return false;
    }

    private String extractUserKey(String msg) {
        String[] parts = msg.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("for") && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return "unknown";
    }
}
