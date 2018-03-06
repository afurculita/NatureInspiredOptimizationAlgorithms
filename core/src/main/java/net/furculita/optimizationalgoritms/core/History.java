package net.furculita.optimizationalgoritms.core;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.Arrays;
import java.util.LinkedList;

public class History extends LinkedList<Individual> {
    public Individual getBest() {
        return this.getLast();
    }

    @Override
    public String toString() {
        return "Best: " + this.getBest().toString()
                + "\nIndividuals: " + Arrays.toString(this.toArray());
    }
}
