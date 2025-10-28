package com.cber.analyzer;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;

public class SimpleTailer {
    private final File file;
    private final LogHandler handler;
    private boolean running = true;

    public interface LogHandler {
        void handle(String line);
    }

    public SimpleTailer(String filePath, LogHandler handler) {
        this.file = new File(filePath);
        this.handler = handler;
    }

    public void start() {
        try (RandomAccessFile reader = new RandomAccessFile(file, "r")) {
            long filePointer = file.length();
            while (running) {
                long fileLength = file.length();
                if (fileLength < filePointer) {
                    filePointer = fileLength; // Log file rotated
                } else if (fileLength > filePointer) {
                    reader.seek(filePointer);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        handler.handle(line);
                    }
                    filePointer = reader.getFilePointer();
                }
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Tailer stopped: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
