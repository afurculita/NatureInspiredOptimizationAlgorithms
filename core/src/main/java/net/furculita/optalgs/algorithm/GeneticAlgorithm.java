package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.algorithm.crossover.CrossoverStrategy;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Population;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm extends Algorithm {
    private double mutationRate;

    private static final int MAX_ITERATIONS = 1000;
    CrossoverStrategy crossoverStrategy;

    public GeneticAlgorithm(CrossoverStrategy crossoverStrategy, double mutationRate) {
        super();
        this.crossoverStrategy = crossoverStrategy;
        this.mutationRate = mutationRate;
    }

    @Override
    public History solve(Problem problem) {
        Population population = Population.potential(problem);
        History history = new History();

        int k = 0;
        Individual currentBest = population.get(0);
        history.add(currentBest);

        do {
            population = nextGeneration(problem, population, currentBest);

            k++;
            Individual iterationBest = population.getFittest();

            System.out.println(k + ": " + iterationBest);

            if (iterationBest.betterThan(currentBest)) {
                currentBest = iterationBest;
                history.add(currentBest);
            }
        } while (k < MAX_ITERATIONS);

        return history;
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

        for (int i = 0; i < population.size(); i++) {
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
}
