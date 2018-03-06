package net.furculita.optimizationalgoritms.core;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.LinkedList;

public class History extends LinkedList<Individual> {
    public Individual getBest() {
        return this.getFirst();
    }

    @Override
    public String toString() {
        return "History{" +
                "all=" + this.listIterator() +
                "\n\nbest=" + this.getLast() +
                '}';
    }
}
