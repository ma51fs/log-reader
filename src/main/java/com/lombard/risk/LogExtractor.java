package com.lombard.risk;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.lang.Long.parseLong;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toMap;

public class LogExtractor {

    private static final int FILE_INDEX_LABEL = 2;
    private static final int FILE_INDEX_LATENCY = 13;

    public Collection<Log> extractSummary(final String filePath) throws IOException {
        return lines(get(filePath))
                .skip(1)
                .map(mapToLog)
                .collect(toMapLog())
                .values();
    }

    private Collector<Log, ?, Map<String, Log>> toMapLog() {
        return toMap(Log::getLabel, log -> log, (currentLog, newLog) -> {
            currentLog.addLatency(newLog.getLatencyTotal());
            return currentLog;
        });
    }

    private Function<String, Log> mapToLog = (line) -> {
        String[] lineData = line.split(",");
        return new Log(lineData[FILE_INDEX_LABEL], parseLong(lineData[FILE_INDEX_LATENCY]));
    };
}
