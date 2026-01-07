package it.vladastos;

import java.io.InputStream;
import java.util.Scanner;

import it.vladastos.ArgParser.ParsedArgs;
import it.vladastos.exceptions.InputFileException;
import it.vladastos.exceptions.SolverNotFoundException;
import it.vladastos.exceptions.UnimplementedException;

/**
 * Abstract base class for all solvers
 * Uses the same input for both parts
 * 
 */
public abstract class DaySolver {

    private String input;
    private ParsedArgs args;
    private void setArgs(ParsedArgs args) {
        this.args = args;
    }

    private static final String CLASS_NAME = "it.vladastos.solutions.day%d.Solver";
    private static final String INPUT_FILE_NAME = "day%d_input.txt";

    public static DaySolver fromParsedArgs(ParsedArgs args) throws SolverNotFoundException, InputFileException  {
        // Use reflection to get the class name
        String className = String.format(CLASS_NAME, args.day());
    
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
        } catch (ReflectiveOperationException e) {
            throw new SolverNotFoundException("Error instantiating class: " + className, e);
        }

        // Initialize the input
        daySolver.setArgs(args);
        daySolver.initInput();

        return daySolver;

    }

    public String solve() {
        int part = this.args.part();
        if (part == 1) {
            return solvePart1();
        } else{
            return solvePart2();
        }
    }


    private void initInput() throws InputFileException  {
        if (this.args == null) {
            throw new IllegalStateException("Args not set");
        }

        int day = this.args.day();
        
        String fileName = String.format(INPUT_FILE_NAME, day);
        
        // Load the file from the resources
        InputStream inputStream = DaySolver.class.getResourceAsStream("/" + fileName);
        if (inputStream == null) {
            throw new InputFileException("Input file not found: " + fileName);
        }
        try (Scanner scanner = new Scanner(inputStream)) {
            this.input = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            throw new InputFileException("Error reading input file: " + fileName, e);
        }
    }


    public String getInput() {
        return this.input;
    }

    

    public String solvePart1() {
        throw new UnimplementedException("Not implemented");
    }
    
    public String solvePart2() {
        throw new UnimplementedException("Not implemented");
    }

}