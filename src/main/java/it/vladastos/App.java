package it.vladastos;
import it.vladastos.daysolver.DaySolver;

public class App {
    static String DAY_PREFIX = "day";
    static String PART_PREFIX = "part";

    /**
     * Record to hold the parsed arguments
     * Values are validated in the parseArgs method, so they are guaranteed to be valid
     */
    public record ParsedArgs(int day, int part) {}

    public static void main(String[] args) {

       
        // Parse the arguments
        ParsedArgs parsedArgs;
        try {
            parsedArgs = parseArgs(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            printUsage();
            return;
        }

        // Create the solver using the factory method
        DaySolver daySolver;
        try {
            daySolver = DaySolver.forDay(parsedArgs.day);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        // Solve the puzzle and print the solution
        String solution;
        try {
            solution = daySolver.solve(parsedArgs.part);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println(solution);
    }

    /**
     * Parse the arguments
     * Expects 2 arguments, day and part.
     * 
     * Throws IllegalArgumentException if the arguments are invalid
     * @param args
     * @return ParsedArgs
     */
    private static ParsedArgs parseArgs(String[] args) throws IllegalArgumentException {
        
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        if (args.length == 1) {
            throw new IllegalArgumentException("Not enough arguments provided");
        }
        if (args.length > 2) {
            throw new IllegalArgumentException("Too many arguments provided");
        }

        // Parse the arguments
        String day = args[0];
        String part = args[1];

        if (day.startsWith(DAY_PREFIX)) {
            day = day.substring(DAY_PREFIX.length());
        }

        if (part.startsWith(PART_PREFIX)) {
            part = part.substring(PART_PREFIX.length());
        }

        int dayInt;
        int partInt;

        try {
            dayInt = Integer.parseInt(day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid day argument: " + day);
        }

        try {
            partInt = Integer.parseInt(part);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid part argument: " + part);
        }

        return new ParsedArgs(dayInt, partInt);
    }

    private static void printUsage() {
        System.err.println("Usage: java App <day> <part>");
    }
}
