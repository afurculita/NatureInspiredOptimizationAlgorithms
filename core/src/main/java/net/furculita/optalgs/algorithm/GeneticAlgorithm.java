package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.algorithm.crossover.CrossoverStrategy;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Population;
import net.furculita.optalgs.problem.Problem;
import net.furculita.optalgs.problem.StateResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm extends Algorithm {
    private double mutationRate;

    private static final int MAX_ITERATIONS = 500;
    CrossoverStrategy crossoverStrategy;

    public GeneticAlgorithm(CrossoverStrategy crossoverStrategy, double mutationRate) {
        super();
        this.crossoverStrategy = crossoverStrategy;
        this.mutationRate = mutationRate;
    }

    @Override
    public StateResult solve(Problem problem) {
        Population population = Population.potential(problem);
        StateResult<Individual> state = new StateResult<>();

        int k = 0;
        state.addBest(population.get(0));

        do {
            population = nextGeneration(problem, population, state.getBest());

            k++;
            Individual iterationBest = population.getFittest();

            if (iterationBest.betterThan(state.getBest())) {
                state.addBest(iterationBest);
            } else {
                state.add(iterationBest);
            }
        } while (k < MAX_ITERATIONS);

        return state;
    }

    protected Population nextGeneration(Problem problem, Population population, Individual currentBest) {
        Population selectedPop = doSelection(population, currentBest);
        Population mutatedPop = doMutation(selectedPop);

        return crossoverStrategy.crossover(mutatedPop, problem);
    }

    Population doSelection(Population population, Individual currentBest) {
        Population selectedPop = new Population();
        List<Double> fitnessWeights = new ArrayList<>();
        double fitnessTotal = 0d;
        double prevProbability = 0d;

        for (Individual ind : population) {
            fitnessTotal += ind.getFitness();
        }

        for (Individual ind : population) {
            double currProb = prevProbability + ind.getFitness() / fitnessTotal;

            fitnessWeights.add(currProb);
            prevProbability = currProb;
        }

        Collections.sort(population);

        for (int i = 0; i < population.initSize(); i++) {
            double r = Randoms.percentage();
            for (int j = 0; j < fitnessWeights.size(); j++) {
                if (r < fitnessWeights.get(j)) {
                    selectedPop.add(Individual.clone(population.get(j)));
                    break;
                }
            }
        }

        if (currentBest != null && !selectedPop.contains(currentBest)) {
            selectedPop.set(0, Individual.clone(currentBest));
        }

        return selectedPop;
    }

    Population doMutation(Population population) {
        Population mutatedPop = new Population(population);

        for (Individual ind : mutatedPop) {
            double r = Randoms.percentage();

            if (r < mutationRate) {
                mutatedPop.set(mutatedPop.indexOf(ind), Individual.mutateOneRandomGeneFromOneRandomChromosome(ind));
            }
        }

        return mutatedPop;
    }

    @Override
    public String toString() {
        return "GeneticAlgorithm{" +
                "mutationRate=" + mutationRate +
                ", crossoverStrategy=" + crossoverStrategy.getClass().getSimpleName() +
                '}';
    }
}
