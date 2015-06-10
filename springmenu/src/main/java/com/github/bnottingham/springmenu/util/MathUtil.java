package com.github.bnottingham.springmenu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brett Nottingham on 4/13/15
 *         Copyright (c) 2015 Nottingham Software, Inc. All rights reserved.
 */
public class MathUtil
{
    private static Map<Double, Double> sDistanceMap = new HashMap<>();

    /**
     * Convert degrees to radians for the x-axis
     *
     * @param degrees to use
     * @return x value in radians
     */
    public static double getX(final double degrees)
    {
        return Math.cos(Math.toRadians(degrees));
    }

    /**
     * Convert degrees to radians for the y-axis
     *
     * @param degrees to use
     * @return y-value in radians
     */
    public static double getY(final double degrees)
    {
        return Math.sin(Math.toRadians(degrees));
    }

    /**
     * Get the y-value of the curve given the current x and the total distance of the curve
     * Equation used: y = size * x^1/2
     * @param x-value to use
     * @param totalDistanceFromOrigin of the curve (total distance from the origin of the furthest point on the curve)
     * @return y-value
     */
    public static double getCurveY(final double x, final double totalDistanceFromOrigin)
    {
        double current = x / totalDistanceFromOrigin;
        return totalDistanceFromOrigin * Math.pow(current, .5);
    }

    /**
     * Get the x value that when plugged into getCurveY() would return a distance to (0,0) equal to the given distance
     * TODO: I did this via, brute force because its been 20 years since I took high school geometry.  I know there are ways to solve quadratics for distance but my brain hurt after a bit trying to do the math.  While this is a little inefficient, it gets the job done.
     *
     * @param distanceFromOrigin distance from the origin of this point
     * @param totalDistanceFromOrigin of the curve (total distance from the origin of the furthest point on the curve)
     * @return x-value that would return the given distance
     */
    public static double getCurveXGivenDistance(final double distanceFromOrigin, final double totalDistanceFromOrigin)
    {
        if (sDistanceMap.containsKey(distanceFromOrigin))
        {
            return sDistanceMap.get(distanceFromOrigin);
        }

        double x = 0;

        while (true)
        {
            double y = getCurveY(x, distanceFromOrigin);
            double tempDistance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            double diff = tempDistance - distanceFromOrigin;

            if (diff > 0)
            {
                sDistanceMap.put(distanceFromOrigin, x);
                return x;
            }
            x += .01;
        }
    }
}
