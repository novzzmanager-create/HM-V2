package com.hypers.hm;

import java.util.concurrent.*;
import java.util.*;

public class gipsGet {

    public static double run(long durationMs) {

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        List<Future<Long>> futures = new ArrayList<>();

        long start = System.nanoTime();
        long end = start + durationMs * 1_000_000;

        for (int i = 0; i < cores; i++) {

            futures.add(executor.submit(() -> {

                long ops = 0;
                double x = 1.001;

                while (System.nanoTime() < end) {
                    x = Math.sqrt(x * x + 1.2345);
                    ops++;
                }

                return ops;
            }));
        }

        long totalOps = 0;

        try {
            for (Future<Long> f : futures) {
                totalOps += f.get();
            }
        } catch (Exception ignored) {}

        executor.shutdown();

        double seconds = durationMs / 1000.0;
        return (totalOps / seconds) / 1_000_000_000.0;
    }
}