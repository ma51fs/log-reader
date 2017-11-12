package com.lombard.risk;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogPrinter.class})
public class LogPrinterTest {

    private static final String FILE_PATH = "filepath";

    @Mock
    private Path mockPath;

    private LogPrinter logPrinter = new LogPrinter();

    @Test
    public void given_a_list_of_log_summaries_then_write_to_file() throws IOException {
        mockStatic(Files.class);

        logPrinter.writeSummary(FILE_PATH, logSummaries());

        verifyStatic(Files.class);

        write(get(FILE_PATH), expectedSummaryLogs());
    }

    @Test
    public void given_a_list_of_empty_log_summaries_then_do_nothing() throws IOException {
        logPrinter.writeSummary(FILE_PATH, emptyList());
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(mockPath);
    }

    private List<String> expectedSummaryLogs() {
        return asList(
                "Label, Count, Average",
                "LABEL1, 1, 1",
                "LABEL2, 1, 2"
        );
    }

    private Collection<Log> logSummaries() {
        return asList(
                new Log("LABEL1", 1L),
                new Log("LABEL2", 2L)
        );
    }
}
