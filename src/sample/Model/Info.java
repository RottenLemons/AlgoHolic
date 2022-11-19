package sample.Model;

public final class Info {
    public static long startTime, endTime, time;

    public static void startSort() {
        startTime =  System.currentTimeMillis();
    }

    public static void endSort() {
        endTime = System.currentTimeMillis();
        time = endTime - startTime;
    }
}
