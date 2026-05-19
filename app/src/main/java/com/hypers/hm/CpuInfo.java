package com.hypers.hm;

import java.util.*;

public class CpuInfo {

    private static long[][] last = null;

    public static float[] getCoreUsage() {

        try {
            String out = ShizukuUtil.exec("cat /proc/stat");

            if (out == null) return null;

            String[] lines = out.split("\n");

            List<long[]> current = new ArrayList<>();

            for (String line : lines) {

                if (!line.startsWith("cpu")) continue;
                if (line.startsWith("cpu ")) continue;

                String[] toks = line.trim().split("\\s+");

                long idle = Long.parseLong(toks[4]);

                long total = 0;
                for (int i = 1; i < toks.length; i++) {
                    total += Long.parseLong(toks[i]);
                }

                current.add(new long[]{idle, total});
            }

            if (current.size() == 0) return null;

            // first read → belum bisa hitung
            if (last == null) {
                last = current.toArray(new long[0][0]);
                return null;
            }

            int cores = current.size();
            float[] usage = new float[cores];

            for (int i = 0; i < cores; i++) {

                long idle = current.get(i)[0];
                long total = current.get(i)[1];

                long prevIdle = last[i][0];
                long prevTotal = last[i][1];

                long diffIdle = idle - prevIdle;
                long diffTotal = total - prevTotal;

                float cpu = 0f;

                if (diffTotal > 0) {
                    cpu = (1f - ((float) diffIdle / diffTotal)) * 100f;
                }

                usage[i] = Math.max(0f, Math.min(cpu, 100f));
            }

            last = current.toArray(new long[0][0]);

            return usage;

        } catch (Exception e) {
            return null;
        }
    }
}