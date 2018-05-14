package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Vector;
import org.moeaframework.problem.tsplib.TSPInstance;

import java.util.Collections;
import java.util.List;

public class TravellingSalesmanProblem extends Problem {
    private final TSPInstance tspInstance;

    public TravellingSalesmanProblem(TSPInstance tspInstance) {
        super(tspInstance.getDimension());
        this.tspInstance = tspInstance;
    }

    @Override
    public double evaluate(Individual subject) {
        return 0;
    }

    @Override
    public double evaluate(double[] coordinates) {
        return 0;
    }

    @Override
    public String solutionsToString(List<Chromosome> solution) {
        return null;
    }

    @Override
    public int chromosomeSize() {
        return 0;
    }

    public Vector randomVector() {
        Vector position = new Vector(this.getDimension());
        for (int i = 0; i < this.getDimension(); i++) {
            position.add(i, (double) i);
        }

        Collections.shuffle(position);

        return position;
    }
}
