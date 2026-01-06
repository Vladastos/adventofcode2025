package it.vladastos.solutions.day2;

import java.util.ArrayList;
import java.util.List;

import it.vladastos.DaySolver;

public class Solver extends DaySolver {

    public String solvePart1() {
        
        List<IdRange> ranges = parseInput(this.getInput());

        List<Long> invalidIds = new ArrayList<>();
        for (IdRange range : ranges) {
            invalidIds.addAll(getInvalidIds(range));
        }
;
        return String.valueOf(invalidIds.stream().reduce(0L, Long::sum));
    }

    public String solvePart2() {
        List<IdRange> ranges = parseInput(this.getInput());

        List<Long> invalidIds = new ArrayList<>();
        for (IdRange range : ranges) {
            invalidIds.addAll(getInvalidIdsPart2(range));
        }
        return String.valueOf(invalidIds.stream().reduce(0L, Long::sum));
    }

    private record IdRange(long min, long max) {
        @Override
        public final String toString() {
            return min + " - " + max;
        }
    }
    
    private List<IdRange> parseInput(String input) {
        List<IdRange> result = new ArrayList<>();

        // Strip the newlines
        input = input.replaceAll("\n", "");

        for (String range : input.split(",")) {
            String[] parts = range.split("-");
            result.add(new IdRange(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }
        return result;
    }


    // Part 1

    private List<Long> getInvalidIds(IdRange range) {
        List<Long> result = new ArrayList<>();
        for (long id = range.min(); id <= range.max(); id++) {
            if (isInvalid(id)) {
                result.add(id);
            }
        }
        return result;
    }

    private boolean isInvalid(long id) {
        
        // If the id is obtainable by concatenating two identical strings, it is invalid
        
        String str = String.valueOf(id);

        // If the length of the string is odd, it is valid
        if (str.length() % 2 == 1) {
            return false;
        }

        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(i + str.length() / 2)) {
                return false;
            }
        }
        // If the above loop doesn't return false, the string is obtainable by concatenating two identical strings so it is invalid

        System.out.println("Found invalid id: " + id + ". " + str.substring(0, str.length() / 2) + " == " + str.substring(str.length() / 2));
        return true;
    }

    // Part 2

    private List<Long> getInvalidIdsPart2(IdRange range) {
        List<Long> result = new ArrayList<>();
        for (long id = range.min(); id <= range.max(); id++) {
            if (isInvalidPart2(id)) {
                result.add(id);
            }
        }
        return result;
    }

    private boolean isInvalidPart2(long id) {
        
        // If the id is obtainable by concatenating any number of identical strings, it is invalid
        
        String str = String.valueOf(id);

        // Iterate through the first half of the string as there is no need to go further than that
        boolean isObtainable = false;
        for (int i = 0; i < str.length() / 2; i++) {

            // If the string length is divisible by i + 1, check if the string is obtainable by concatenating strings of length i + 1
            if (str.length() % (i + 1) == 0) {
                int times = str.length() / (i + 1);
                // System.out.println("String "+ str + " could be obtainable by concatenating " + times + " strings of length " + (i + 1));
                if (checkIfObtainable(str, i + 1, times)) {
                    isObtainable = true;
                    break;
                }


            }
            
        }
        // If the above loop doesn't return false, the string is obtainable by concatenating two identical strings so it is invalid
        return isObtainable;
    }

    private boolean checkIfObtainable(String str, int length, int times) {

        // Cycle through couples of substrings until a difference is found. When finding a difference, return false

        for (int substringNo = 0; substringNo < times - 1; substringNo++) {
            // Cycle through the characters of the i and i + 2 substrings
            int startOfFirstSubstring = substringNo * length;
            int endOfFirstSubstring = (substringNo*length) + length;

            int startOfSecondSubstring = (substringNo*length) + length;
            int endOfSecondSubstring = (substringNo*length) + (length * 2);


            String first = str.substring(startOfFirstSubstring, endOfFirstSubstring);
            String second = str.substring(startOfSecondSubstring, endOfSecondSubstring );

            //System.out.println("Checking if string " + first + " == " + second);

            if (!first.equals(second)) {
                //System.out.println("Found different substrings: " + first + " != " + second);
                return false;
            }

        }
        System.out.println("String " + str + " is obtainable by concatenating " + times + " times the string " + str.substring(0, length));
        return true;
    }
}
