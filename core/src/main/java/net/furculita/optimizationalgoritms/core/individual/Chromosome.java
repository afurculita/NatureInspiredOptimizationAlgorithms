package net.furculita.optimizationalgoritms.core.individual;

import java.util.BitSet;
import java.util.Random;

public class Chromosome extends BitSet {
    private static final long serialVersionUID = 1L;
    private int initialBitCount;

    private Chromosome(int nBits) {
        super(nBits);
        initialBitCount = nBits;
    }

    public static Chromosome generateNewChromosome(int size) {
        Chromosome output = new Chromosome(size);
        Random r = new Random();
        for (int i = 0; i < output.getInitialBitCount(); i++) {
            boolean value = r.nextBoolean();
            output.set(i, value);
        }

        return output;
    }

    public static Chromosome clone(Chromosome initialIndiv) {
        Chromosome output = new Chromosome(initialIndiv.getInitialBitCount());
        output.clear();
        output.or(initialIndiv);

        return output;
    }

    public static String toString(Chromosome i) {
        return i.toBitString();
    }

    public static String toString(Chromosome[] config) {
        StringBuffer buf = new StringBuffer();
        for (Chromosome i : config) {
            buf.append(i.toBitString()).append(" ");
        }
        return buf.toString();
    }

    public static String toString(Chromosome[][] configList) {
        StringBuffer buf = new StringBuffer();
        for (Chromosome[] config : configList) {
            for (Chromosome i : config) {
                buf.append(i.toBitString()).append(" ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    public int getInitialBitCount() {
        return initialBitCount;
    }

    public String toBitString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < getInitialBitCount(); i++) {
            if (get(i)) {
                builder.append("1");
            } else {
                builder.append("0");
            }
        }
        return builder.toString();
    }

    public int asDecimal() {
        int output = 0;
        int multiplier = 1;
        for (int i = getInitialBitCount() - 1; i >= 0; i--) {
            if (get(i)) {
                output += multiplier;
            }
            multiplier *= 2;
        }

        return output;
    }


}
