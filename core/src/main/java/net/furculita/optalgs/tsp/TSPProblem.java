package net.furculita.optalgs.tsp;

import org.moeaframework.problem.tsplib.NodeCoordinates;
import org.moeaframework.problem.tsplib.TSPInstance;

import java.util.Collections;

public class TSPProblem {
    private NodeCoordinates distanceTable;
    private int salesmanNr;
    private int dimension;

    public TSPProblem(TSPInstance tspInstance, int salesmanNr) {
//        NodeCoordinates dt = tspInstance.getDistanceTable();
//
//        this.distanceTable = new NodeCoordinates(
//                dt.getSize() + 1 + salesmanNr, dt.getType(), dt.getDistanceFunction());
//
//        this.distanceTable.add(new Node(0, 0, 0));
//
//        dt.getNodes().forEach((Integer key, Node node) -> {
//            this.distanceTable.add(node);
//        });
//
//        // add K virtual nodes
//        for (int i = 1; i <= salesmanNr; i++) {
//            this.distanceTable.add(new Node(dt.getSize() + i, true, 0, 0));
//        }
        this.distanceTable = tspInstance.getDistanceTable();

        this.dimension = this.distanceTable.getSize();
        this.salesmanNr = salesmanNr;
    }

    public boolean isBetterThan(double fitness, double otherValue) {
        return otherValue < fitness;
    }

    public Vector randomVector() {
        Vector position = new Vector(this.dimension);
        for (int i : this.distanceTable.listNodes()) {
            position.add((double) i);
        }

        Collections.shuffle(position);

        return position;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getSalesmanNr() {
        return this.salesmanNr;
    }

    public NodeCoordinates getDistanceTable() {
        return distanceTable;
    }
}
