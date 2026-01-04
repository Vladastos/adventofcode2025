package it.vladastos;
import it.vladastos.exceptions.InputFileException;
import it.vladastos.exceptions.SolverNotFoundException;

public class App {

    public static void main(String[] args) throws SolverNotFoundException, InputFileException {
        // Parse the arguments
        ParsedArgs parsedArgs;
        try {
            parsedArgs = ParsedArgs.parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printUsage();
            return;
        }

        // Create the solver using the factory method
        DaySolver daySolver = DaySolver.fromParsedArgs(parsedArgs);

        // Solve the puzzle and print the solution
        String solution = daySolver.solve();
        
        System.out.println(solution);
    }


    private static void printUsage() {
        System.err.println("Usage: java App <day> <part>");
    }
}
