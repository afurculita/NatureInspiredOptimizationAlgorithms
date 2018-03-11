package net.furculita.optimizationalgoritms.core.algorithm.mutator;

import net.furculita.optimizationalgoritms.core.individual.Chromosome;
import net.furculita.optimizationalgoritms.core.individual.Individual;
import net.furculita.optimizationalgoritms.core.problem.Problem;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class BitBasedRepresentationFlipMutator extends Mutator {
    @Override
    public Individual mutate(Individual parent, Problem problem) {
        Individual mutated = new Individual(parent);
        List<Chromosome> genome = mutated.getChromosomes();

        Random randomGenerator = new Random();

        int randomGeneIndex = randomGenerator.nextInt(genome.size());

        Chromosome mutatedGene = genome.get(randomGeneIndex);
        mutatedGene.flip(randomGenerator.nextInt(mutatedGene.getInitialBitCount()));

        genome.set(randomGeneIndex, mutatedGene);
        mutated.setChromosomes(genome);

        return mutated;
    }
}
