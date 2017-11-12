package com.lombard.risk;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class LogRunnerTest {

    private static final String FILE_LOG = "file.txt";
    private static final String FILE_SUMMARY = "summary.txt";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private LogExtractor logExtractor;

    @Mock
    private LogPrinter logPrinter;

    private LogRunner logRunner;

    @Before
    public void setUp() throws Exception {
        logRunner = new LogRunner(logExtractor, logPrinter);
    }

    @Test
    public void given_log_file_generate_summary_file() throws Exception {
        when(logExtractor.extractSummary(FILE_LOG)).thenReturn(logSummaries());

        logRunner.generateSummary(FILE_LOG, FILE_SUMMARY);

        verify(logExtractor).extractSummary(FILE_LOG);

        verify(logPrinter).writeSummary(FILE_SUMMARY, logSummaries());
    }

    @Test
    public void given_log_file_but_no_summary_file() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Please provide summary output file");

        logRunner.generateSummary(FILE_LOG, "");
    }

    @Test
    public void given_no_log_file() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Please provide log file path");

        logRunner.generateSummary("", FILE_SUMMARY);
    }

    @After
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(logExtractor, logPrinter);
    }

    private Collection<Log> logSummaries() {
        return asList(
                new Log("LABEL1", 1L),
                new Log("LABEL2", 2L)
        );
    }
}
