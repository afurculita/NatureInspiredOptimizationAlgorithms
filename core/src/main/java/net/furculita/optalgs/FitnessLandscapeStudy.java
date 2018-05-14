package net.furculita.optalgs;

import net.furculita.optalgs.algorithm.GreedyAscentHillClimbing;
import net.furculita.optalgs.algorithm.SteepestAscentHillClimbing;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.FitnessLandscapeStudyProblem;
import net.furculita.optalgs.problem.Problem;
import net.furculita.optalgs.problem.StateResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FitnessLandscapeStudy {
    public static void main(String[] args) {
        StateResult<Individual> stateResult;

        Map<String, List<Integer>> greedyResults = new HashMap<>();
        Map<String, List<Integer>> saResults = new HashMap<>();

        Problem problem = new FitnessLandscapeStudyProblem();

        for (int i = 0; i < 32; i++) {
            Individual firstInd = Individual.generateFromNumber(i, problem);

            stateResult = new GreedyAscentHillClimbing(firstInd).solve(problem);
            registerSolution(stateResult, greedyResults, i);

            stateResult = new SteepestAscentHillClimbing(firstInd).solve(problem);
            registerSolution(stateResult, saResults, i);
        }

        System.out.println("Greedy");
        System.out.println(greedyResults);
        System.out.println("SteepestAscent");
        System.out.println(saResults);
    }

    private static void registerSolution(StateResult<Individual> result, Map<String, List<Integer>> map, int i) {
        String sol = result.getBest().getSolution();

        if (map.containsKey(sol)) {
            map.get(sol).add(i);

            return;
        }

        List<Integer> list = new ArrayList<>();
        list.add(i);

        map.put(sol, list);
    }
}
