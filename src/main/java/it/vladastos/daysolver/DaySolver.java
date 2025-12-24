package it.vladastos.daysolver;

public abstract class DaySolver {

    public static DaySolver forDay(int day) throws Exception {
        // Use reflection to get the class name
        String className = "it.vladastos.solutions.day" + day + ".Solver";
        Class<?> clazz;
        // Load the class
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new Exception("Could not find solution class: " + className, e);
        }
        // Instantiate the class
        try {
            return (DaySolver) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Exception("Error instantiating class: " + className, e);
        }

    }

    public String solve(int part) throws Exception {
        if (part == 1) {
            // TODO get input
            String input = "";
            return solvePart1(input);
        } else if (part == 2) {
            // TODO get input
            String input = "";
            return solvePart2(input);
        } else {
            throw new Exception("Invalid part: " + part);
        }
    }

    public String solvePart1(String input) {
        throw new RuntimeException("Not implemented");
    };
    
    public String solvePart2(String input) {
        throw new RuntimeException("Not implemented");
    };

}