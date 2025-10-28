package com.cber.analyzer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ThreatAnalyzer analyzer = new ThreatAnalyzer();
            AlertManager alertManager = new AlertManager();

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
                        System.out.println("Starting analyzer... (Press Ctrl+C to stop)");
                        SimpleTailer tailer = new SimpleTailer("/var/log/system.log", line -> {
                            LogEntry entry = LogParser.parse(line);
                            if (entry != null && analyzer.isSuspicious(entry)) {
                                Alert alert = new Alert("Suspicious login pattern detected", entry);
                                alertManager.sendAlert(alert);
                            }
                        });
                        tailer.start();
                        break;

                    case "2":
                        alertManager.showAlertHistory();
                        break;

                    case "3":
                         // call LogViewer, pass default file path (adjust as needed)
                        String defaultLog = "/var/log/system.log";
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
