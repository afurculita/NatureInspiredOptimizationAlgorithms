package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Individual;

import java.util.Arrays;
import java.util.LinkedList;

public class History extends LinkedList<Individual> {
    private Individual getBest() {
        return this.getLast();
    }

    @Override
    public String toString() {
        return "Best: " + this.getBest().toString()
                + "\nFitness history: \n" + Arrays.toString(this.toArray());
    }
}
