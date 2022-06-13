package org.metapod.ftracker.utils;


public class Mass {
    private static final float LB_TO_KG_RATIO = 2.2046226218f;

    public static double poundsToKilograms(int lb) {
        return lb / LB_TO_KG_RATIO;
    }

    public static double kilogramsToPounds(int kg) {
        return kg * LB_TO_KG_RATIO;
    }

    public static long kilogramsToPoundsRounded(long kg) {
        return Math.round(kg * LB_TO_KG_RATIO);
    }
}
