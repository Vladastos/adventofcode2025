package it.vladastos.daysolver;

import java.util.Scanner;

/**
 * Abstract base class for all solvers
 * 
 */
public abstract class DaySolver {

    private String input;

    public static DaySolver forDay(int day) throws Exception {
        // Use reflection to get the class name
        String className = "it.vladastos.solutions.day" + day + ".Solver";
    
        // Load the class
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new Exception("Could not find solution class: " + className, e);
        }
    
        // Instantiate the class
        DaySolver daySolver;
        try {
            daySolver = (DaySolver) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Exception("Error instantiating class: " + className, e);
        }

        // Initialize the input
        try {
            daySolver.initInput(day);  
        } catch (Exception e) {
            throw new Exception("Error initializing input for day:" + day+ " " + e.getMessage(), e);
        }

        return daySolver;

    }

    public String solve(int part) throws Exception {
        if (part == 1) {
            return solvePart1();
        } else if (part == 2) {
            return solvePart2();
        } else {
            throw new Exception("Invalid part: " + part);
        }
    }


    private void initInput(int day) {
        
        String fileName = "day" + day + "_input.txt";
        
        // Load the file from the resources
        java.io.InputStream inputStream = DaySolver.class.getResourceAsStream("/" + fileName);
        if (inputStream == null) {
            throw new RuntimeException("Could not find input file for day " + day);
        }

        Scanner scanner = new Scanner(inputStream);
        this.input = scanner.useDelimiter("\\A").next();
        scanner.close();

    }

    public String getInput() {
        return this.input;
    }

    public String solvePart1() {
        throw new RuntimeException("Not implemented");
    };
    
    public String solvePart2() {
        throw new RuntimeException("Not implemented");
    };

}