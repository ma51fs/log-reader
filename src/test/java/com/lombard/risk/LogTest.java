package com.lombard.risk;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LogTest {

    @Test
    public void given_a_log_calculate_average_latency() throws Exception {
        Log log = new Log("TEST", 1000L);

        log.addLatency(500L);
        log.addLatency(300L);

        assertTrue(600 == log.averageLatency());
    }

    @Test
    public void given_a_log_print_summary() throws Exception {
        Log log = new Log("TEST", 1000L);

        log.addLatency(500L);
        log.addLatency(300L);

        assertThat("TEST, 3, 600", is(log.summaries()));
    }
}
