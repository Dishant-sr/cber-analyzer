package com.cber.analyzer;

public class Alert {
    private final String message;
    private final LogEntry logEntry;

    public Alert(String message, LogEntry logEntry) {
        this.message = message;
        this.logEntry = logEntry;
    }

    public String getMessage() { return message; }
    public LogEntry getLogEntry() { return logEntry; }

    @Override
    public String toString() {
        return "ALERT: " + message + " | Log: " + logEntry.toString();
    }
}
