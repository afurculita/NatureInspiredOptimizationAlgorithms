package net.furculita.optimizationalgoritms.consoleapp;

import net.furculita.optimizationalgoritms.core.History;
import net.furculita.optimizationalgoritms.core.Solver;
import net.furculita.optimizationalgoritms.core.problem.GriewangkProblem;
import net.furculita.optimizationalgoritms.core.problem.RastriginProblem;
import net.furculita.optimizationalgoritms.core.problem.RosenbrockValleyProblem;
import net.furculita.optimizationalgoritms.hillclimbing.HillClimbingAlgorithm;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        History history;

        System.out.println("-----------------------------------------------");
        System.out.println("RastriginProblem");
        history = solver.solve(new RastriginProblem(30), new HillClimbingAlgorithm());
        System.out.println(history);

        System.out.println("-----------------------------------------------");
        System.out.println("GriewangkProblem");
        history = solver.solve(new GriewangkProblem(30), new HillClimbingAlgorithm());
        System.out.println(history);

        System.out.println("-----------------------------------------------");
        System.out.println("RosenbrockValleyProblem");
        history = solver.solve(new RosenbrockValleyProblem(30), new HillClimbingAlgorithm());
        System.out.println(history);
    }
}
