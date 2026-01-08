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
        String solution = daySolver.solve();
    
        logger.info("Solution: " + solution);
    
    }


    private static void printUsage() {
        System.err.println("Usage: java App <day> <part> [-d|--debug] [-t|--test]");
    }
}
