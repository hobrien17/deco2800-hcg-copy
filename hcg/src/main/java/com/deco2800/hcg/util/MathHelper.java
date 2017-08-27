package com.deco2800.hcg.util;

/**
 * Helper class with some useful math functions.
 */
public class MathHelper {
    
    /**
     * Clamps the given {@code input} between {@code min} and {@code max}, inclusive.
     * @param input
     *          The value to be clamped.
     * @param min
     *          The upper bound of the output.
     * @param max
     *          The lower bound of the output.
     * @return the {@code input} value clamped between {@code min} and {@code max}
     */
    public static int clamp(int input, int min, int max) {
        if(input >= max) {
            return max;
        } else if(input <= min) {
            return min;
        }
        
        return input;
    }
}
