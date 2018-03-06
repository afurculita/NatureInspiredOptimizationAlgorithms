package net.furculita.optimizationalgoritms.core.algorithm.mutator;

import net.furculita.optimizationalgoritms.core.individual.Individual;
import net.furculita.optimizationalgoritms.core.problem.Problem;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class BitBasedRepresentationFlipMutator extends Mutator {
    @Override
    public Individual mutate(Individual parent, Problem problem) {
        Individual mutated = new Individual(parent);
        List<Double> genome = mutated.getGenome();

        int randomGeneIndex = new Random().nextInt(genome.size());
        double parentGene = genome.get(randomGeneIndex);
        double mutatedGene;

        do {
            mutatedGene = mutateGene(parentGene);
        } while (!problem.isValid(mutatedGene));

        genome.set(randomGeneIndex, mutatedGene);
        mutated.setGenome(genome);

        return mutated;
    }

    private double mutateGene(Double randomGene) {
        String binary = Long.toBinaryString(Double.doubleToRawLongBits(randomGene));

        binary = flipRandomBit(binary);

        return Double.longBitsToDouble(parseBinary(binary));
    }

    private static long parseBinary(String s) {
        return new BigInteger(s, 2).longValue();
    }

    private static String flipRandomBit(String bits) {
        StringBuilder builder = new StringBuilder(bits);

        int bitToFlip = new Random().nextInt(bits.length());

        builder.setCharAt(bitToFlip, (char) (bits.charAt(bitToFlip) ^ 1));

        return builder.toString();
    }
}
