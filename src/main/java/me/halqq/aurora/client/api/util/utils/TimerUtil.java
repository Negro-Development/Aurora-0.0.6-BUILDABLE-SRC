// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.util.utils;

public class TimerUtil {

    long startTime = System.currentTimeMillis();
    long delay = 0L;
    boolean paused = false;
    private long time = -1L;

    public boolean isPassed() {
        return !this.paused && System.currentTimeMillis() - this.startTime >= this.delay;
    }

    public boolean passed(final long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }

    public long getTime(final long time) {
        return time / 1000000L;
    }

    public void resetDelay() {
        this.startTime = System.currentTimeMillis();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean passedMs(long ms) {
        return this.passedNS(this.convertToNS(ms));
    }

    public boolean passedMS(final long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public boolean hasPassed(double ms) {
        return System.currentTimeMillis() - time >= ms;
    }

    public long convertToNS(long time) {
        return time * 1000000L;
    }
}
