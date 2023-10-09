package MazeExplorer.Core;

import java.util.Random;

/**
 * A library of static methods to generate pseudo-random numbers from
 * different distributions (bernoulli, uniform, gaussian, discrete,
 * and exponential). Also includes methods for shuffling an array and
 * other randomness related stuff you might want to do. Feel free to
 * modify this file.
 * <p>
 * Adapted from https://introcs.cs.princeton.edu/java/22library/StdRandom.java.html
*
 */
public class RandomUtils {

    /**
     * Returns a random real number uniformly in [0, 1).
     *
     * @return a random real number uniformly in [0, 1)
     */
    public static double uniform(Random random) {
        return random.nextDouble();
    }

    /**
     * Returns a random integer uniformly in [0, n).
     *
     * @param n number of possible integers
     * @return a random integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static int uniform(Random random, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        return random.nextInt(n);
    }


    /**
     * Returns a random long integer uniformly in [0, n).
     *
     * @param n number of possible {@code long} integers
     * @return a random long integer uniformly between 0 (inclusive) and {@code n} (exclusive)
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public static long uniform(Random random, long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }

        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
        long r = random.nextLong();
        long m = n - 1;

        // power of two
        if ((n & m) == 0L) {
            return r & m;
        }

        // reject over-represented candidates
        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }
        return r;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC METHODS BELOW RELY ON JAVA.UTIL.RANDOM ONLY INDIRECTLY VIA
    //  THE STATIC METHODS ABOVE.
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns a random integer uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random integer uniformly in [a, b)
     * @throws IllegalArgumentException if {@code b <= a}
     * @throws IllegalArgumentException if {@code b - a >= Integer.MAX_VALUE}
     */
    public static int uniform(Random random, int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random, b - a);
    }

    /**
     * Returns a random real number uniformly in [a, b).
     *
     * @param a the left endpoint
     * @param b the right endpoint
     * @return a random real number uniformly in [a, b)
     * @throws IllegalArgumentException unless {@code a < b}
     */
    public static double uniform(Random random, double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random) * (b - a);
    }
}