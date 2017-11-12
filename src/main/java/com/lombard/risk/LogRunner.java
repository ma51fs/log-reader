package com.lombard.risk;

import java.io.IOException;
import java.util.Collection;

import static org.apache.commons.lang3.Validate.notEmpty;

public class LogRunner {

    private final LogExtractor logExtractor;
    private final LogPrinter logPrinter;

    public LogRunner(final LogExtractor logExtractor, final LogPrinter logPrinter) {
        this.logExtractor = logExtractor;
        this.logPrinter = logPrinter;
    }

    public void generateSummary(final String fileReadPath, final String writePath) throws IOException {
        notEmpty(fileReadPath, "Please provide log file path");
        notEmpty(writePath, "Please provide summary output file");

        System.out.println("Reading file: " + fileReadPath);

        Collection<Log> logs = logExtractor.extractSummary(fileReadPath);

        System.out.println("Summary output to file: " + writePath);

        logPrinter.writeSummary(writePath, logs);
    }

    public static void main(String[] args) throws IOException {
        new LogRunner(new LogExtractor(), new LogPrinter()).generateSummary(args[0], args[1]);
    }
}
