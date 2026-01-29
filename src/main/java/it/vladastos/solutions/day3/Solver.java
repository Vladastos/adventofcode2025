package it.vladastos.solutions.day3;

import java.util.ArrayList;
import java.util.List;

import it.vladastos.AbstractSolver;

public class Solver extends AbstractSolver {
    
    @Override
    public String solvePart1() {
        String input = getInput();
        List<List<Integer>> parsedInput = parseInput(input);

        return parsedInput.stream().map(line -> getResultForLineGeneric(line,2)).reduce(0, Integer::sum).toString();
        //return parsedInput.stream().map(this::getResultForLinePart1).reduce(0, Integer::sum).toString();
    }

    @Override
    public String solvePart2() {
        String input = getInput();
        List<List<Integer>> parsedInput = parseInput(input);
        return parsedInput.stream().map(line -> getResultForLineGeneric(line, 2)).reduce(0, Integer::sum).toString();    
    }
    private Integer getResultForLinePart1(List<Integer> line) {
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
        logger.fine(String.valueOf((max * 10) + secondMax));
        return (max * 10) + secondMax;
    }

    private Integer getResultForLineGeneric(List<Integer> line,Integer resultLength) {
        List<Integer> result = new ArrayList<>(resultLength);
        // Given a list of numbers A and the length N of the result (for example 2)
        // We want to get the maximum possible number obtained by concatenating N numbers from the list A without changing the order of the numbers
        // 
        // For example, if N = 2, we want to get the maximum number obtained by concatenating two numbers from the list [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        //
        // The algorithm for finding the value of result[i] is as follows:
        //
        // 1. We take a sublist of the list A starting from the index of result[i - 1]( if i = 0, the sublist starts at 0) and ending at size - resultLength + i + 1
        // 2. We take the maximum number from the sublist
        // 3. We add the maximum number to the result

        Integer lastIndex = 0;
        logger.fine("Line: " + line);
        for (int i = 0; i < resultLength; i++) {
            List<Integer> sublist = line.subList(lastIndex, line.size() - resultLength + i + 1);;
            logger.fine("Sublist: " + sublist);
            Integer max = sublist.stream().max(Integer::compare).get();
            result.add(max);
            // Store the index of the found maximum number
            lastIndex = line.indexOf(max) + 1;
        }

        // The result is obtained by concatenating the numbers in the result list
        Integer finalResult = 0;
        for (int i = 0; i < result.size(); i++) {
            finalResult = finalResult * 10 + result.get(i);
        }
        logger.fine("Result: " + result);
        logger.fine("Final result: " + finalResult);
        return finalResult;
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
