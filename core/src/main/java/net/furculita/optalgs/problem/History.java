package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Individual;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class History extends LinkedList<Individual> {
    public Individual getBest() {
        if (this.isEmpty()) {
            return null;
        }

        return this.getLast();
    }

    @Override
    public String toString() {
        return "Best: " + this.getBest()
                + "\nFitness history: \n"
                + this.stream()
                .map(Individual::toString)
                .collect(Collectors.joining("\n"));
    }
}
