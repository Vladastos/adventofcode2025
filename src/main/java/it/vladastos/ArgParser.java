package it.vladastos;

import java.util.ArrayList;
import java.util.List;

/**
 * Record to hold the parsed arguments
 * Values are validated in the parseArgs method, so they are guaranteed to be valid
 */
public class ArgParser {

    private static final String DAY_PREFIX = "day";
    private static final String PART_PREFIX = "part";
    
    public static ParsedArgs parseArgs (String[] args)  throws IllegalArgumentException {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        if (args.length == 1) {
            throw new IllegalArgumentException("Not enough arguments provided");
        }

        // Parse the positional arguments first, assume that the flags are at the end
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

        // Validate the arguments
        if (dayInt < 1 || dayInt > 25) {
            throw new IllegalArgumentException("Invalid day argument. Must be between 1 and 25: " + day);
        }
        if (partInt < 1 || partInt > 2) {
            throw new IllegalArgumentException("Invalid part argument. Must be between 1 and 2: " + part);
        }
        
        // Parse flags
        
        boolean test = false;
        boolean benchmark = false;
        boolean debug = false;
        List<String> argsWithoutFlags = new ArrayList<>();


        for(String arg:args) {
            if (arg.startsWith("-")) {
                if (arg.equals("-d") || arg.equals("--debug")) {
                    debug = true;
                } else if (arg.equals("-t") || arg.equals("--test")) {
                    test = true;
                } else if (arg.equals("-b") || arg.equals("--benchmark")) {
                    benchmark = true;
                } else {
                    throw new IllegalArgumentException("Invalid flag: " + arg);
                }
            }
            else {
                argsWithoutFlags.add(arg);
            }
        }


        // Parse the arguments
        if (argsWithoutFlags.size() != 2) {
            throw new IllegalArgumentException("Expected 2 arguments, got " + argsWithoutFlags.size());
        }

        
        return new ParsedArgs(dayInt, partInt, debug, test, benchmark);
    }

    public record ParsedArgs(int day, int part, boolean debug, boolean test, boolean benchmark) {
        public ParsedArgs(int day, int part) {
            this(day, part, false, false, false);
        }
    }

}
