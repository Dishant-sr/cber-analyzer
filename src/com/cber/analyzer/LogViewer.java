package com.cber.analyzer;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

/**
 * Utility to show log lines from a file filtered by a time window.
 */
public class LogViewer {

    // ISO pattern (e.g. 2025-10-21T10:23:15Z)
    private static final Pattern ISO_TS = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)\\b.*$");
    // Syslog-like pattern: e.g. "Oct 27 18:02:13" (no year)
    private static final Pattern SYSLOG_TS = Pattern.compile("^([A-Z][a-z]{2}\\s+\\d{1,2}\\s+\\d{2}:\\d{2}:\\d{2})\\b.*$");

private static final DateTimeFormatter ISO_FMT = DateTimeFormatter.ISO_INSTANT;
private static final DateTimeFormatter SYSLOG_FMT =
        new java.time.format.DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("MMM d HH:mm:ss")
                .parseDefaulting(java.time.temporal.ChronoField.YEAR, 2000)
                .toFormatter(Locale.ENGLISH);

    /**
     * Interactive method: asks user for timeframe and file, then prints matching lines.
     * @param defaultPath fallback log file path if user presses enter
     */
    public static void showLogsInteractive(String defaultPath) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("\n--- Show Logs ---");
            System.out.print("Log file path (enter for default [" + defaultPath + "]): ");
            String path = sc.nextLine().trim();
            if (path.isEmpty()) path = defaultPath;

            System.out.println("Choose timeframe to display:");
            System.out.println("1) Last 1 hour");
            System.out.println("2) Last 24 hours");
            System.out.println("3) Last N days");
            System.out.println("4) Custom range (start & end timestamps, ISO format)");
            System.out.print("Choice (1-4): ");
            String choice = sc.nextLine().trim();

            Instant now = Instant.now();
            Instant from = null;
            Instant to = now;

            try {
                switch (choice) {
                    case "1":
                        from = now.minus(Duration.ofHours(1));
                        break;
                    case "2":
                        from = now.minus(Duration.ofDays(1));
                        break;
                    case "3":
                        System.out.print("Enter N (days): ");
                        int n = Integer.parseInt(sc.nextLine().trim());
                        from = now.minus(Duration.ofDays(Math.max(1, n)));
                        break;
                    case "4":
                        System.out.print("Enter start timestamp (ISO, e.g. 2025-10-21T00:00:00Z): ");
                        String s = sc.nextLine().trim();
                        System.out.print("Enter end timestamp (ISO) or leave blank for now: ");
                        String e = sc.nextLine().trim();
                        from = Instant.parse(s);
                        to = e.isEmpty() ? now : Instant.parse(e);
                        break;
                    default:
                        System.out.println("Invalid choice. Aborting.");
                        return;
                }
            } catch (Exception ex) {
                System.out.println("Invalid input: " + ex.getMessage());
                return;
            }

            System.out.println("\nShowing log lines from " + from + " to " + to + " in file: " + path + "\n");

            Path p = Paths.get(path);
            if (!Files.exists(p)) {
                System.out.println("File not found: " + path);
                return;
            }

            // stream file and print matching lines
            try (BufferedReader br = Files.newBufferedReader(p)) {
                String line;
                int printed = 0;
                while ((line = br.readLine()) != null) {
                    Instant ts = extractTimestamp(line);
                    if (ts != null && !ts.isBefore(from) && !ts.isAfter(to)) {
                        System.out.println(line);
                        printed++;
                    }
                }
                if (printed == 0) System.out.println("[No log lines found in that time window]");
            } catch (IOException ioe) {
                System.out.println("Failed to read file: " + ioe.getMessage());
            }
        }
    }

    /**
     * Try to extract an Instant from a log line.
     * Supports ISO (full) and syslog (month day HH:mm:ss) formats (assumes current year).
     */
    public static Instant extractTimestamp(String line) {
        if (line == null || line.isEmpty()) return null;
        Matcher mIso = ISO_TS.matcher(line);
        if (mIso.matches()) {
            String ts = mIso.group(1);
            try {
                return Instant.parse(ts);
            } catch (DateTimeParseException ex) {
                return null;
            }
        }
        Matcher mSys = SYSLOG_TS.matcher(line);
        if (mSys.matches()) {
            String tsPart = mSys.group(1);
            try {
                // syslog has no year; assume current year. handle possible year-wrap (Dec->Jan) could be improved.
                int year = Year.now().getValue();
                LocalDateTime ldt = LocalDateTime.parse(tsPart, SYSLOG_FMT);
                // combine year + parsed time
                LocalDateTime withYear = ldt.withYear(year);
                return withYear.toInstant(ZoneOffset.UTC);
            } catch (DateTimeException ex) {
                return null;
            }
        }
        // fallback: try to find an ISO-like substring anywhere
        Matcher mAnyIso = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)").matcher(line);
        if (mAnyIso.find()) {
            try {
                return Instant.parse(mAnyIso.group(1));
            } catch (DateTimeParseException ex) { /* ignore */ }
        }
        return null;
    }
}
