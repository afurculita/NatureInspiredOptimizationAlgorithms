package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Population;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm extends Algorithm {
    private double crossoverRate;
    private double mutationRate;

    private static final double MAX_FITNESS = 2000;
    private static final int MAX_ITERATIONS = 100;

    public GeneticAlgorithm(double crossOverRate, double mutationRate) {
        super();
        this.crossoverRate = crossOverRate;
        this.mutationRate = mutationRate;
    }

    @Override
    public History solve(Problem problem) {
        Population population = Population.potential(problem);
        History history = new History();

        int k = 0;
        Individual currentBest = null;
        do {
            population = doSelection(population, currentBest);
            population = doMutation(population);
            population = doCrossover(population, problem);

            k++;
            Individual iterationBest = population.getElite();

            if (iterationBest.betterThan(currentBest)) {
                currentBest = iterationBest;
                history.add(currentBest);
            }
        } while (currentBest.getFitness() < MAX_FITNESS && k < MAX_ITERATIONS);

        return history;
    }

    private Population doSelection(Population population, Individual currentBest) {
        Population selectedPop = new Population();

        Random random = new Random();
        List<Double> fitnessWeights = population.getFitnessWeights();

        for (int i = 0; i < population.size(); i++) {
            double r = random.nextDouble();
            for (int j = 0; j < fitnessWeights.size(); j++) {
                if (r < fitnessWeights.get(j)) {
                    selectedPop.add(population.get(j));
                    break;
                }
            }
        }

        if (currentBest != null && !selectedPop.contains(currentBest)) {
            selectedPop.add(currentBest);
        }

        return selectedPop;
    }

    private Population doMutation(Population population) {
        Population mutatedPop = new Population(population);
        Random random = new Random();

        for (Individual ind : mutatedPop) {
            double r = random.nextDouble();

            if (r < mutationRate) {
                mutatedPop.set(mutatedPop.indexOf(ind), Individual.mutateOneRandomGeneFromOneRandomChromosome(ind));
            }
        }

        return mutatedPop;
    }

    private Population doCrossover(Population population, Problem problem) {
        Population crossedPop = new Population(population);
        Random random = new Random();
        List<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            double r = random.nextDouble();
            if (r < crossoverRate) {
                selectedIndexes.add(i);
            }
        }

        while (selectedIndexes.size() > 1) {
            int randomInt = random.nextInt(selectedIndexes.size());
            int index1 = selectedIndexes.get(randomInt);
            selectedIndexes.remove(Integer.valueOf(index1));

            randomInt = random.nextInt(selectedIndexes.size());
            int index2 = selectedIndexes.get(randomInt);
            selectedIndexes.remove(Integer.valueOf(index2));

            Individual crossoverResult = performCrossover(population.get(index1), population.get(index2), problem);
            crossedPop.set(index1, Individual.clone(crossoverResult));
            crossedPop.set(index2, Individual.clone(crossoverResult));
        }

        return crossedPop;
    }

    private Individual performCrossover(Individual x, Individual y, Problem problem) {
        List<Chromosome> childChroms = new ArrayList<>();
        Random random = new Random();

        int selectedIndividualIndex = random.nextInt(x.getChromosomes().size());
        int selectedGeneIndex = random.nextInt(x.getChromosomeSize());

        boolean firstShouldBegin = random.nextBoolean();
        Individual first, second;
        if (firstShouldBegin) {
            first = x;
            second = y;
        } else {
            first = y;
            second = x;
        }

        for (int i = 0; i < x.getChromosomes().size(); i++) {
            if (i < selectedIndividualIndex) {
                childChroms.add(Chromosome.clone(first.chromosome(i)));
            } else if (i > selectedIndividualIndex) {
                childChroms.add(Chromosome.clone(second.chromosome(i)));
            } else {
                Chromosome middle = Chromosome.generateNewChromosome(x.getChromosomeSize());
                for (int j = 0; j < x.getChromosomeSize(); j++) {
                    if (j < selectedGeneIndex) {
                        middle.set(j, first.chromosome(i).get(j));
                    } else {
                        middle.set(j, second.chromosome(i).get(j));
                    }
                }
                childChroms.add(middle);
            }
        }

        return new Individual(childChroms, problem);
    }
}