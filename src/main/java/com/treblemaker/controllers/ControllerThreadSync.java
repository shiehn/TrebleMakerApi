package com.treblemaker.controllers;

public class ControllerThreadSync {

    private static ControllerThreadSync instance = null;
    private static Object mutex = new Object();

    private ControllerThreadSync() {
    }

    public static ControllerThreadSync getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) instance = new ControllerThreadSync();
            }
        }
        return instance;
    }
}
