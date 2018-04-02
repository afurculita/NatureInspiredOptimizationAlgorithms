package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Item;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class History extends LinkedList<Item> {
    private Item getBest() {
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
                .map(Item::toString)
                .collect(Collectors.joining("\n"));
    }
}
