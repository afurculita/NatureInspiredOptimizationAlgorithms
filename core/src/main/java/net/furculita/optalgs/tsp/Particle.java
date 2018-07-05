package net.furculita.optalgs.tsp;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Item;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.moeaframework.problem.tsplib.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Particle implements Item {
    private Vector position;
    private Vector bestPosition;

    private Vector velocity;
    private double bestFitness;
    private TSPProblem problem;

    private List<Pair<Integer, Integer>> swaps = new ArrayList<>();
    private List<Tour> tourPosition = new ArrayList<>();
    private List<Tour> bestTourPosition;

    Particle(Particle particle) {
        this.position = particle.position.copy();
        this.bestPosition = particle.bestPosition.copy();

        this.bestFitness = particle.bestFitness;
        this.velocity = particle.velocity.copy();
        this.problem = particle.problem;

        this.bestTourPosition = new ArrayList<>(particle.bestTourPosition);
        this.tourPosition = new ArrayList<>(particle.tourPosition);
        this.swaps = new ArrayList<>();
    }

    public Particle(TSPProblem problem) {
        this.problem = problem;

        position = problem.randomVector();
        bestPosition = position;

        generateTours();
        generateSwapsList(problem.getDimension());
        this.bestTourPosition = new ArrayList<>(this.tourPosition);

        velocity = new Vector(problem.getDimension());
        for (int i = 0; i < problem.getDimension(); i++) {
            velocity.add(i, Randoms.between(-6, 6));
        }
        bestFitness = this.evaluate();
    }

    private void generateSwapsList(int dim) {
        Random random = new Random();
        int ra1;
        int ra2;

        int ra = random.nextInt(65535) % dim;
        for (int j = 0; j < ra; j++) {
            ra1 = random.nextInt(65535) % dim;
            while (ra1 == 0) {
                ra1 = random.nextInt(65535) % dim;
            }
            ra2 = random.nextInt(65535) % dim;
            while (ra1 == ra2 || ra2 == 0) {
                ra2 = random.nextInt(65535) % dim;
            }

            swaps.add(new ImmutablePair<>(ra1, ra2));
        }
    }

    private void generateTours() {
        for (int i = 0; i < problem.getSalesmanNr(); i++) {
            tourPosition.add(new Tour());
        }

        Vector visitedNodes = new Vector(position);

        while (!visitedNodes.isEmpty()) {
            int rNodeIndex = Randoms.integer(visitedNodes.size());
            int rTourIndex = Randoms.integer(tourPosition.size());

            tourPosition.get(rTourIndex).add(visitedNodes.get(rNodeIndex).intValue());

            visitedNodes.remove(rNodeIndex);
        }
    }

    public void updatePersonalBest(double bestFitness) {
        double fitness = this.evaluate();
        if (problem.isBetterThan(this.bestFitness, fitness) && fitness != bestFitness) {
            bestPosition = position.copy();

            this.bestTourPosition = new ArrayList<>(this.tourPosition);

            this.bestFitness = fitness;
        }
    }

    public double evaluate() {
        double longestTourLength = 0;
        double allToursLength = 0;

        for (Tour tour : this.tourPosition) {
            double l = tour.distance(this.problem.getDistanceTable());

            allToursLength += l;

            if (l > longestTourLength) {
                longestTourLength = l;
            }
        }

        return (longestTourLength * this.problem.getSalesmanNr() + allToursLength);
    }

    private double getLongestTourLength() {
        double longestTourLength = 0;

        for (Tour tour : this.tourPosition) {
            double l = tour.distance(this.problem.getDistanceTable());

            if (l > longestTourLength) {
                longestTourLength = l;
            }
        }

        return longestTourLength;
    }

    private double getLength() {
        double len = 0;

        for (Tour tour : this.tourPosition) {
            len += tour.distance(this.problem.getDistanceTable());
        }

        return len;
    }

    public Vector getPosition() {
        return position;
    }

    public List<Tour> getBestTourPosition() {
        return bestTourPosition;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getBestPosition() {
        return bestPosition;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Max: " + (this.getLongestTourLength() + " - total: " + getLength());
    }

    @Override
    public double getValue() {
        return this.getBestFitness();
    }

    public List<Pair<Integer, Integer>> getSwaps() {
        return swaps;
    }

    public void setSwaps(List<Pair<Integer, Integer>> swaps) {
        this.swaps = swaps;
    }

    private Vector vectorizeTours(List<Tour> tours) {
        Vector v = new Vector(this.problem.getDimension());

        for (Tour tour : tours) {
            for (int node : tour.toArray()) {
                v.add((double) node);
            }
        }

        return v;
    }

    public List<Pair<Integer, Integer>> computeSwapDifference(List<Tour> tours) {
        return this.vectorizeTours(this.tourPosition).findSwapDifference(this.vectorizeTours(tours));
    }

    public void applySwap(Pair<Integer, Integer> swap) {
        Vector v = this.vectorizeTours(this.tourPosition);

        double temp = v.get(swap.getLeft());
        v.set(swap.getLeft(), v.get(swap.getRight()));
        v.set(swap.getRight(), temp);

        List<Tour> newTours = new ArrayList<>();
        int k = 0;

        for (Tour tour : this.tourPosition) {
            Tour t = new Tour();

            int max = k + tour.size();
            for (int i = k; i < max; i++) {
                t.add(v.get(i).intValue());
                k++;
            }
            newTours.add(t);
        }

        this.tourPosition = newTours;
    }
}
