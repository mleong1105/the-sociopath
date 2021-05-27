package com.nintendods.util;

import java.util.Random;

public class Util {
    private static final Random rand = new Random();

    public static int randomBetween(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    // Ex. given 11:41 and 59 min returns 12:40
    public static int reformatTime(int time, int min) {
        int hours = Integer.parseInt(Integer.toString(time).substring(0, 2));
        int minutes = Integer.parseInt(Integer.toString(time).substring(2, 4)) + min;

        hours += minutes / 60;
        minutes = minutes % 60;

        return Integer.parseInt(hours + (minutes < 10 ? "0" + minutes : String.valueOf(minutes)));
    }
}
