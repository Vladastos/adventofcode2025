package it.vladastos.daysolver;

import java.io.InputStream;
import java.util.Scanner;

import it.vladastos.exceptions.InputFileException;
import it.vladastos.exceptions.SolverNotFoundException;
import it.vladastos.exceptions.UnimplementedException;

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
            throw new SolverNotFoundException("Could not find solution class: " + className, e);
        }
    
        // Instantiate the class
        DaySolver daySolver;
        try {
            daySolver = (DaySolver) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new SolverNotFoundException("Error instantiating class: " + className, e);
        }

        // Initialize the input
        
        daySolver.initInput(day);

        return daySolver;

    }

    public String solve(int part) throws IllegalArgumentException, UnimplementedException {
        if (part < 1 || part > 2) {
            throw new IllegalArgumentException("Invalid part. Expected 1 or 2, got " + part);
        }
        if (part == 1) {
            return solvePart1();
        } else{
            return solvePart2();
        }
    }


    private void initInput(int day) throws InputFileException  {

        String fileName = "day" + day + "_input.txt";
        
        // Load the file from the resources
        InputStream inputStream = DaySolver.class.getResourceAsStream("/" + fileName);
        if (inputStream == null) {
            throw new InputFileException("Input file not found: " + fileName);
        }
        try (Scanner scanner = new Scanner(inputStream)) {
            this.input = scanner.useDelimiter("\\A").next();
            
        scanner.close();
        } catch (Exception e) {
            throw new InputFileException("Error reading input file: " + fileName, e);
        }
    }


    public String getInput() {
        return this.input;
    }

    public String solvePart1() throws UnimplementedException{
        throw new UnimplementedException("Not implemented");
    };
    
    public String solvePart2() throws UnimplementedException{
        throw new UnimplementedException("Not implemented");
    };

}