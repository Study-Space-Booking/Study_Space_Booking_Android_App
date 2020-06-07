package com.placeholder.study_space_booking_android_app.util;

public class ThreadUtil {
    public static void sleep(long t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void exec(Runnable runnable){
        new Thread(runnable).start();
    }
}
