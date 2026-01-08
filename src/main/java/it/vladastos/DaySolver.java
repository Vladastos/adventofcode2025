package it.vladastos;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Logger logger;

    private String input;
    private ParsedArgs args;
    private void setArgs(ParsedArgs args) {
        this.args = args;
    }

    private static final String CLASS_NAME = "it.vladastos.solutions.day%d.Solver";
    private static final String INPUT_FILE_NAME = "day%d/input.txt";
    private static final String TEST_INPUT_FILE_NAME= "day%d/test_input.txt";
    private static final String TEST_RESULT_FILE_NAME = "day%d/test_result_part%d.txt";
    
    
    /**  
     * Factory method. The only way to get a valid solver
     * All the initialization is done here 
     **/ 
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

        // Set the arguments
        daySolver.setArgs(args);

        // Set the logger
        daySolver.logger = Logger.getLogger(daySolver.getClass().getName());
        daySolver.logger.setLevel(args.debug() ? Level.FINE : Level.INFO);

        // Initialize the input
        int day = args.day();

        String fileName = args.test() ? String.format(TEST_INPUT_FILE_NAME, day) : String.format(INPUT_FILE_NAME, day);
        
        // Load the file from the resources
        InputStream inputStream = DaySolver.class.getResourceAsStream("/" + fileName);
        if (inputStream == null) {
            throw new InputFileException("Input file not found: " + fileName);
        }
        try (Scanner scanner = new Scanner(inputStream)) {
            daySolver.input = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            throw new InputFileException("Error reading input file: " + fileName, e);
        }

        return daySolver;

    }

    // Main method of the solver. Calls solvePart1() or solvePart2() depending on the part
    public String solve() {
        int part = this.args.part();
        
        logger.fine("Solving day " + this.args.day() + " and part " + part);
        String result;
        if (part == 1) {
            result = solvePart1();
        } else{
            result = solvePart2();
        }
        if (this.args.test()) {
            performTest(result);    
        }
        return result;
    }

    public void performTest(String result) {

        try {
            if (checkTestResult(result)) {
                logger.info("✅ Test passed for day " + this.args.day() + " and part " + this.args.part());
            } else {
                logger.info("❌ Test failed for day " + this.args.day() + " and part " + this.args.part());
            }
        } catch (InputFileException e) {
            logger.info("Could not check test result for day " + this.args.day() + " and part " + this.args.part() + ": " + e.getMessage());
        }
    }

    private boolean checkTestResult(String result) throws InputFileException {
        int day = this.args.day();
        int part = this.args.part();
        String fileName = String.format(TEST_RESULT_FILE_NAME, day, part);
        InputStream inputStream = DaySolver.class.getResourceAsStream("/" + fileName);
        if (inputStream == null) {
            throw new InputFileException("Test result file not found: " + fileName);
        }
        try (Scanner scanner = new Scanner(inputStream)) {
            String expected = scanner.useDelimiter("\\A").next();
            return expected.equals(result);
        } catch (Exception e) {
            throw new InputFileException("Error reading test result file: " + fileName, e);
        }

    }
    
    public String getInput() {
        return this.input;
    }

    // Must be implemented by subclasses
    public String solvePart1() {
        throw new UnimplementedException("Not implemented");
    }
    
    public String solvePart2() {
        throw new UnimplementedException("Not implemented");
    }
}