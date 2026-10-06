package com.bank.monitoring;

public final class PerformanceMonitor {

    private PerformanceMonitor() {
    }

    public static long start() {
        return System.nanoTime();
    }

    public static void end(long startTime) {

        long elapsed = System.nanoTime() - startTime;

        System.out.println(
                "Execution time: "
                        + toMilliseconds(elapsed)
                        + " ms"
        );
    }

    public static double toMilliseconds(long nanoseconds) {
        return nanoseconds / 1_000_000.0;
    }
}