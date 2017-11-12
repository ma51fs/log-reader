package com.lombard.risk;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Log {

    private final String label;

    private long latencyTotal;
    private int latencyCount;

    public Log(final String label, final long latencyTotal) {
        this.label = label;
        this.latencyTotal = latencyTotal;
        this.latencyCount = 1;
    }

    public String getLabel() {
        return label;
    }

    public int getLatencyCount() {
        return latencyCount;
    }

    public long getLatencyTotal() {
        return latencyTotal;
    }

    public void addLatency(final long latency) {
        this.latencyTotal += latency;
        this.latencyCount++;
    }

    public String summaries() {
        return label + ", " + latencyCount + ", " + averageLatency();
    }

    public long averageLatency() {
        return latencyTotal / latencyCount;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return reflectionEquals(this, obj);
    }
}
