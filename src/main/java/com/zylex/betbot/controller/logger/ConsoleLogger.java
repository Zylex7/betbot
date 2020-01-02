package com.zylex.betbot.controller.logger;

import com.zylex.betbot.exception.ConsoleLoggerException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base class for loggers.
 */
public abstract class ConsoleLogger {

    private volatile static String logOutput;

    static AtomicLong programStartTime = new AtomicLong(System.currentTimeMillis());

    static {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm a");
        LocalDateTime startDateTime = LocalDateTime.now();
        logOutput = "\n" + StringUtils.repeat("*", 50) + "\n"
                + String.format("Bot started at: %s", startDateTime.format(formatter))
                + "\n" + StringUtils.repeat("-", 50) + "\n";
    }

    /**
     * Write log to file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeToLogFile() {
        try {
            File logFile = new File("log.txt");
            logFile.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(logOutput);
            }
        } catch (IOException e) {
            throw new ConsoleLoggerException(e.getMessage(), e);
        }
    }

    private synchronized static void addToLog(String line) {
        if (line.contains("\b")) {
            int backspaces = StringUtils.countMatches(line, "\b");
            int lastNewLineIndex = logOutput.lastIndexOf("\n");
            logOutput = logOutput.substring(0, Math.max(logOutput.length() - lastNewLineIndex, backspaces + 1))
                    + line.replace("\b", "");
        } else {
            logOutput += line;
        }
    }

    /**
     * Write exception in log.
     * @param message - stack of exception.
     */
    public synchronized static void writeExceptionToLog(String message) {
        logOutput += "\n" + message;
    }

    public static void endMessage() {
        writeInLine(String.format("\nBot work completed in %s", computeTime(programStartTime.get())));
    }

    void writeLineSeparator() {
        String line = "\n" + StringUtils.repeat("-", 50);
        writeInLine(line);
    }

    void writeErrorMessage(String line) {
        System.err.print(line);
        addToLog(line);
    }

    static synchronized void writeInLine(String line) {
        System.out.print(line);
        addToLog(line);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    synchronized void pressEnter() {
        try {
            System.in.read();
        } catch (IOException e) {
            throw new ConsoleLoggerException(e.getMessage(), e);
        }
    }

    static String computeTime(long startTime) {
        long millis = System.currentTimeMillis() - startTime;
        String time = String.format("%02d min. %02d sec.",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        if (hours > 0) {
            return String.format("%02d h. ", hours) + time;
        } else {
            return time;
        }
    }
}
