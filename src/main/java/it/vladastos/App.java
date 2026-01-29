package it.vladastos;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import it.vladastos.ArgParser.ParsedArgs;
import it.vladastos.exceptions.InputFileException;
import it.vladastos.exceptions.SolverNotFoundException;

public class App {

    

    public static void main(String[] args) throws SolverNotFoundException, InputFileException {
        // Parse the arguments
        ParsedArgs parsedArgs;
        try {
            parsedArgs = ArgParser.parseArgs(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printUsage();
            return;
        }

        // Print the help message if requested
        if (parsedArgs.help()) {
            printHelp();
            return;
        }
        
        // Initalize the logger
        Logger logger = Logger.getLogger(App.class.getName());
        try {
            
            InputStream inputStream = App.class.getResourceAsStream("/logging.properties");
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (Exception e) {
            System.err.println("Error initializing logger: " + e.getMessage());
            return;
        }
        
        // Create the solver using the factory method
        AbstractSolver daySolver = AbstractSolver.fromParsedArgs(parsedArgs);

        // Solve the puzzle and print the solution
        long start = System.currentTimeMillis();
        String solution = daySolver.solve();
        long end = System.currentTimeMillis();

        if (parsedArgs.benchmark()) {
            logger.info("Execution time: " + (end - start) + "ms");
        }
        
        logger.info("Solution: " + solution);
    
    }


    private static void printHelp() {
        System.err.println("Usage: java App <day> <part> [flags]");
        System.err.println("Example: java App 1 1");
        System.err.println("Flags:");
        System.err.println("  -d, --debug         Enable debug mode");
        System.err.println("  -t, --test          Run test for the specified day and part.");
        System.err.println("                      The test input and test result will be loaded from the resources folder.");
        System.err.println("                      The test input file must be named day<day>/test_input.txt and the test result file must be named day<day>/test_result_part<part>.txt");
        System.err.println("  -b, --benchmark     Print execution time for the specified day and part."); 
        System.err.println("                      This execution time is not very accurate, but it is useful for giving an idea of the performance.");
        System.err.println("  -h, --help          Print this help message");
    }
    private static void printUsage() {
        System.err.println("Usage: java App <day> <part> [flags]"); 
    }
}
