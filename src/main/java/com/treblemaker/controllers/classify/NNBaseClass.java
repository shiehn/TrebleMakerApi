package com.treblemaker.controllers.classify;

public class NNBaseClass {

    public long getAStartTime() {
        long startTime = System.nanoTime();
        return startTime;
    }

    public void logLapsedTime(long startTime, String requestTag) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        //duration = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);

        System.out.print("REQUEST DURATION : " + duration + "ms : -" + requestTag);
    }
}
