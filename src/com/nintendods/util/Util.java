package com.nintendods.util;

import java.util.Random;

public class Util {
    private static final Random rand = new Random();

    public static int randomBetween(int min, int max) {
        return rand.nextInt(max - min) + min;
    }
}
