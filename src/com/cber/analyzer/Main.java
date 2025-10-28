package com.cber.analyzer;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static void processPastLogs(String logFile, ThreatAnalyzer analyzer, AlertManager alertManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = LogParser.parse(line);
                if (entry != null && analyzer.isSuspicious(entry)) {
                    Alert alert = new Alert("Suspicious login pattern detected", entry);
                    alertManager.sendAlert(alert);
                }
            }
            System.out.println("Finished analyzing past logs.");
        } catch (IOException e) {
            System.out.println("Error reading log file: " + e.getMessage());
        }
    }

    private static void startLiveAnalysis(String logFile, ThreatAnalyzer analyzer, AlertManager alertManager) {
        SimpleTailer tailer = new SimpleTailer(logFile, line -> {
            LogEntry entry = LogParser.parse(line);
            if (entry != null && analyzer.isSuspicious(entry)) {
                Alert alert = new Alert("Suspicious login pattern detected", entry);
                alertManager.sendAlert(alert);
            }
        });
        tailer.start();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ThreatAnalyzer analyzer = new ThreatAnalyzer();
            AlertManager alertManager = new AlertManager();

            String defaultLog = "/Users/dishantsinghrathore/Desktop/cber-threat-log-analyzer/src/com/cber/analyzer/fake_auth.log";

            System.out.println("=== Cyber Threat Log Analyzer ===");
            System.out.println("1. Start Analyzer");
            System.out.println("2. Show Alert History");
            System.out.println("3. Show Logs (Time Filtered)");
            System.out.println("4. Exit");

            while (true) {
                System.out.print("\nEnter choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("=== Start Analyzer ===");
                        System.out.println("Choose analysis mode:");
                        System.out.println("1. Analyze past logs only");
                        System.out.println("2. Live analysis only");
                        System.out.println("3. Both past and live");

                        System.out.print("Enter choice: ");
                        String mode = scanner.nextLine();

                        switch (mode) {
                            case "1": // past logs only
                                System.out.println("Analyzing past logs...");
                                processPastLogs(defaultLog, analyzer, alertManager);
                                break;

                            case "2": // live analysis only
                                System.out.println("Starting live analysis... (Press Ctrl+C to stop)");
                                startLiveAnalysis(defaultLog, analyzer, alertManager);
                                break;

                            case "3": // both
                                System.out.println("Analyzing past logs and starting live analysis...");
                                processPastLogs(defaultLog, analyzer, alertManager);
                                startLiveAnalysis(defaultLog, analyzer, alertManager);
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }
                        break;

                    case "2":
                        alertManager.showAlertHistory();
                        break;

                    case "3":
                        // call LogViewer, pass default file path (adjust as needed)
                        LogViewer.showLogsInteractive(defaultLog);
                        break;

                    case "4":
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }
}
