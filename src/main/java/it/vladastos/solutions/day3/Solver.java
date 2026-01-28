package it.vladastos.solutions.day3;

import java.util.ArrayList;
import java.util.List;

import it.vladastos.AbstractSolver;

public class Solver extends AbstractSolver {
    
    @Override
    public String solvePart1() {
        String input = getInput();
        List<List<Integer>> parsedInput = parseInput(input);
        logger.fine(parsedInput.toString());
        
        parsedInput.forEach(line -> logger.fine(getResultForLine(line).toString()));
        return parsedInput.stream().map(this::getResultForLine).reduce(0, Integer::sum).toString();
    }
    private Integer getResultForLine(List<Integer> line) {
        Integer max = 0;
        Integer maxIndex = 0;
        Integer secondMax = 0;

        for (Integer i = 0; i < line.size() - 1; i++) {
            
            if (line.get(i) > max) {
                max = line.get(i);
                maxIndex = i;
            }

        }
        for (Integer i = maxIndex + 1; i < line.size(); i++) {
            if (line.get(i) > secondMax) {
                secondMax = line.get(i);
            }
        }
        return (max * 10) + secondMax;
    }

    private List<List<Integer>> parseInput(String input) {
        List<String> lines = List.of(input.split("\n"));
        List<List<Integer>> result = new ArrayList<>();
        for (String line : lines){
            result.add(parseLine(line));
        }
        return result;
    }

    private List<Integer> parseLine(String line) {
        List<Integer> result = new ArrayList<>();
        for (char c : line.toCharArray()){
            result.add(c - '0');
        }
        return result;
    }
}
