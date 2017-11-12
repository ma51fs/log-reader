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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogExtractor.class})
public class LogExtractorTest {

    private static final String FILE_PATH = "filepath";

    @Mock
    private Path mockPath;

    private LogExtractor logExtractor = new LogExtractor();

    @Test
    public void given_a_list_of_log_generate_a_summary_of_logs() throws IOException {
        mockStatic(Files.class, Paths.class);

        when(get(FILE_PATH)).thenReturn(mockPath);

        when(lines(mockPath)).thenReturn(logRecords());

        List<Log> logSummary = new ArrayList<>(logExtractor.extractSummary(FILE_PATH));

        assertThat(2, is(logSummary.size()));

        assertLog(logSummary.get(0), "Wf_GotoIMArchiveSearchPage", 1, 2997L);

        assertLog(logSummary.get(1), "Wf_SearchIMByDefault", 2, 69614L);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void given_a_list_of_log_which_contain_invalid_data() throws IOException {
        mockStatic(Files.class, Paths.class);

        when(get(FILE_PATH)).thenReturn(mockPath);

        when(lines(mockPath)).thenReturn(logRecordsWithInvalidData());

        logExtractor.extractSummary(FILE_PATH);
    }

    @Test
    public void given_an_empty_list_of_log_generate_a_summary_of_logs() throws IOException {
        mockStatic(Files.class, Paths.class);

        when(get(FILE_PATH)).thenReturn(mockPath);

        when(lines(mockPath)).thenReturn(Collections.<String>emptyList().stream());

        List<Log> logSummary = new ArrayList<>(logExtractor.extractSummary(FILE_PATH));

        assertThat(0, is(logSummary.size()));
    }

    @After
    public void verifyAtEndOfTest() throws Exception {
        verifyStatic(Paths.class);

        get(FILE_PATH);

        verifyStatic(Files.class);

        lines(mockPath);
    }

    private void assertLog(Log log, String label, int count, long average) {
        assertThat(label, is(log.getLabel()));
        assertThat(count, is(log.getLatencyCount()));
        assertThat(average, is(log.getLatencyTotal()));
    }

    private Stream<String> logRecords() {
        return asList(
                "timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect",
                "1484049459387,68629,Wf_SearchIMByDefault,200,OK,Thread Group 1-1,text,true,,85883,1454,1,1,68001,0,170",
                "1484053722119,2045,Wf_SearchIMByDefault,200,OK,Thread Group 1-1,text,true,,87482,1454,1,1,1613,0,226",
                "1484053724173,3564,Wf_GotoIMArchiveSearchPage,200,OK,Thread Group 1-1,text,true,,102248,307,1,1,2997,0,216")
                .stream();
    }

    private Stream<String> logRecordsWithInvalidData() {
        return asList(
                "timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect",
                "1484053724173,3564,Wf_GotoIMArchiveSearchPage,200,OK,Thread Group 1-1,text,true,,102248,307,1,1,2997,0,216",
                "invalid")
                .stream();
    }
}
