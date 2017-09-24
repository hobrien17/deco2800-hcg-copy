package com.deco2800.hcg.util;

/**
 *  Created by Declan on 30/07/2017.
 *  Modified by RLonergan for HCG.
 */
public class Point {
    private float x;
    private float y;

    /**
     * Constructor for the point class.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X coordiante.
     *
     * @return the x coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y coordinate.
     *
     * @return the y coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Equals method.
     *
     * @param o the object to be compared to.
     * @return whether another object is instance of Point and is equal to this.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;

        if (Float.compare(point.x, x) != 0) {
            return false;
        }
        return Float.compare(point.y, y) == 0;
    }

    /**
     * hashcode method.
     *
     * @return the hashcode of a Point object.
     */
    @Override
    public int hashCode() {
        int result = Float.compare(x, +0.0f) != 0 ? Float.floatToIntBits(x) : 0;
        result = 31 * result + (Float.compare(y, +0.0f) != 0 ? Float.floatToIntBits(y) : 0);
        return result;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return the string representation of a Point object.
     */
    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    /**
     * Calculates the distance between two points.
     *
     * @param other the point that will be compared to.
     * @return the distance between the two points.
     */
    public float distanceTo(Point other) {
        float dx = other.x - x;
        float dy = other.y - y;
        return (float)Math.sqrt(dx * dx + dy * dy);
    }
}
