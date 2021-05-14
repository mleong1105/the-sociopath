package com.nintendods.util;

import java.util.Random;

public class Util {
    private static final Random rand = new Random();

    public static int randomBetween(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    // Ex. given 1162 returns 1202
    public static int reformatTime(int time) {
        int hours = Integer.parseInt(Integer.toString(time).substring(0, 2));
        int minutes = Integer.parseInt(Integer.toString(time).substring(2, 4));

        hours += minutes / 60;
        minutes = minutes % 60;

        return Integer.parseInt(hours + (minutes < 10 ? "0" + minutes : String.valueOf(minutes)));
    }
}
