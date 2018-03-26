package net.furculita.optalgs;

import java.util.Random;

public final class Randoms {
    private final static Random random = new Random();

    public static double percentage() {
        return ((double) random.nextInt(100)) / 100;
    }

    public static int integer(int max) {
        return random.nextInt(max);
    }

    public static boolean bool() {
        return random.nextBoolean();
    }
}
