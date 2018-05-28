package net.furculita.optalgs;

import java.util.Random;

public final class Randoms {
    private final static Random random = new Random();

    public static double percentage() {
        return ((double) random.nextInt(100)) / 100;
    }

    public static double next() {
        return random.nextDouble();
    }

    public static int integer(int max) {
        return random.nextInt(max);
    }

    public static boolean bool() {
        return random.nextBoolean();
    }

    public static double between(double beginRange, double endRange) {
        return (endRange - beginRange) * random.nextDouble() + beginRange;
    }
}
