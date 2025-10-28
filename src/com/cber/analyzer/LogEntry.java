package com.cber.analyzer;

public class LogEntry {
    private String timestamp;
    private String process;
    private String message;

    public LogEntry(String timestamp, String process, String message) {
        this.timestamp = timestamp;
        this.process = process;
        this.message = message;
    }

    public String getTimestamp() { return timestamp; }
    public String getProcess() { return process; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "[" + timestamp + "] (" + process + ") " + message;
    }
}
