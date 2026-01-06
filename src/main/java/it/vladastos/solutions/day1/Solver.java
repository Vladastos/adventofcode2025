package it.vladastos.solutions.day1;

import java.util.List;
import it.vladastos.DaySolver;

public class Solver extends DaySolver {
    public String solvePart1() {

        List<Integer> parsedInput = parseInput(this.getInput());
        int result = 0;
        int dialPosition = 50;
        
        for (Integer element : parsedInput) {
            dialPosition += element;
            dialPosition = dialPosition % 100;
            if (dialPosition == 0) {
                result += 1;
            }
        }

        return String.valueOf(result);
    }
    public String solvePart2() {
        List<Integer> parsedInput = parseInput(this.getInput());
        int result = 0;
        int dialPosition = 50;

        for (Integer element : parsedInput) {
            
            if (element == 0) {
                continue;
            }
            int oldDialPosition = dialPosition;
            dialPosition += element;
            int zeroCount = 0;
        
            while (dialPosition < 0 || dialPosition > 99) {
                if (dialPosition < 0) {
                    zeroCount += 1;
                    dialPosition += 100;
                } else {
                    zeroCount += 1;
                    dialPosition -= 100;
                }
            }

            // Check if the dial position is 0 and the element is negative. That would mean that the dial has reached zero by doing a left turn.
            // Without this check, the times the dial reaches zero by doing a left turn would not be counted. 
            if (dialPosition == 0 && element < 0) {
                zeroCount += 1;
            }

            // Check if the starting dial position is 0 and the element is negative. That would mean that the dial was turned to the left starting from zero.
            if (oldDialPosition == 0 && element < 0) {
                zeroCount -= 1;
            }

            result += zeroCount;
        }

        return String.valueOf(result);
    }


    // Parse the input to an array of integers
    private List<Integer> parseInput(String input) {
        // Split the input into lines (windows)
        List<String> lines = List.of(input.split("\n"));
        
        System.out.println("Lines: " + lines.size());
        // Parse the input
        List<Integer> result = lines.stream().map((line) -> {
            if (line.isEmpty()) {
                return 0;
            }

            if (line.startsWith("R")) {
                return Integer.parseInt(line.substring(1));
            } else if (line.startsWith("L")) {
                return -Integer.parseInt(line.substring(1));
            }
            throw new RuntimeException("Something's wrong with the input");
        }).toList();

        return result;
    }
    
}