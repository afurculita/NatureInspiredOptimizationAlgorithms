package net.furculita.optalgs.individual;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;

public class Population extends ArrayList<Individual> {
    private static final int SIZE = 100;

    public Population() {
        super(SIZE);
    }

    public int initSize() {
        return SIZE;
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

    public Individual getFittest() {
        Individual currentBest = this.get(0);

        for (Individual ind : this) {
            if (ind.betterThan(currentBest)) {
                currentBest = ind;
            }
        }

        return currentBest;
    }

    public Individual tournamentSelection() {
        Population tournament = new Population();
        for (int i = 0; i < SIZE / 2; i++) {
            tournament.add(this.get(Randoms.integer(SIZE)));
        }

        return tournament.getFittest();
    }
}
