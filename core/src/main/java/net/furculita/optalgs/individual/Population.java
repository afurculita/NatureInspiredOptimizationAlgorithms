package net.furculita.optalgs.individual;

import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class Population extends ArrayList<Individual> {
    private static final int SIZE = 10;

    private double fitnessesSum = 0d;
    private List<Double> fitnessWeights;
    private List<Double> fitnesses;

    public Population() {
        super(SIZE);
    }

    public Population(Population population) {
        for (Individual ind : population) {
            this.add(Individual.clone(ind));
        }
    }

    public static Population potential(Problem problem) {
        Population population = new Population();

        for (int i = 0; i < SIZE; i++) {
            population.add(Individual.generateNewIndividual(problem));
        }

        return population;
    }

    private void computeFitnessData() {
        if (fitnesses != null) {
            return;
        }

        fitnesses = new ArrayList<>();

        forEach((Individual ind) -> {
            fitnesses.add(ind.getFitness());
            fitnessesSum += ind.getFitness();
        });

        fitnessWeights = new ArrayList<>();

        for (double fit : fitnesses) {
            double val = 0d;
            if (!fitnessWeights.isEmpty()) {
                val += fitnessWeights.get(fitnessWeights.size() - 1);
            }

            fitnessWeights.add(fit / fitnessesSum + val);
        }
    }

    public List<Double> getFitnessWeights() {
        computeFitnessData();

        return fitnessWeights;
    }

    public Individual getElite() {
        final Individual[] currentBest = {this.get(0)};

        forEach((Individual ind) -> {
            if (ind.getFitness() < currentBest[0].getFitness()) {
                currentBest[0] = ind;
            }
        });

        return currentBest[0];
    }
}
