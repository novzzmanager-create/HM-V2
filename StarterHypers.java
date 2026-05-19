package com.hypers.hm;

import com.hypers.hm.debug.HypersDaemon;

public class StarterHypers {

    public static void main(String[] args) {

        HypersDaemon.start();

        while (true) {

            try {

                Thread.sleep(1000);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}