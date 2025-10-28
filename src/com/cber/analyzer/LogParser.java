package com.cber.analyzer;

public class LogParser {

    // Very basic parser; can be improved for different OS log formats
    public static LogEntry parse(String line) {
        if (line == null || line.isEmpty()) return null;

        try {
            String[] parts = line.split(" ", 5);
            if (parts.length < 5) return null;

            String timestamp = parts[0] + " " + parts[1] + " " + parts[2];
            String process = parts[3].replace(":", "");
            String message = parts[4];

            return new LogEntry(timestamp, process, message);
        } catch (Exception e) {
            return null;
        }
    }
}
