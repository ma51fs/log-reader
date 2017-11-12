package com.lombard.risk;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

public class LogPrinter {

    public void writeSummary(final String filePath, final Collection<Log> logs) throws IOException {
        if(logs == null || logs.isEmpty()) {
            return;
        }

        List<String> logSummary = logs
                .stream()
                .map(Log::summaries)
                .collect(toList());

        logSummary.add(0, "Label, Count, Average");

        write(get(filePath), logSummary);
    }
}
