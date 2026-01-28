package it.vladastos;

import java.util.ArrayList;
import java.util.List;

/**
 *  Utility class for parsing command line arguments
 *  
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
        boolean debug = false;
        boolean benchmark = false;
        boolean help = false;
        List<String> argsWithoutFlags = new ArrayList<>();


        for(String arg:args) {
            if (arg.startsWith("-")) {
                if (arg.startsWith("--")) {
                    arg = arg.substring(2);
                    switch (arg) {
                        case "help":
                            help = true;
                            break;
                        case "debug":
                            debug = true;
                            break;
                        case "test":
                            test = true;
                            break;
                        case "benchmark":
                            benchmark = true;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid flag: " + arg);
                    }
                } else {
                    arg = arg.substring(1);
                    for (int i = 0; i < arg.length(); i++) {
                        switch (arg.charAt(i)) {
                            case 'h':
                                help = true;
                            case 'd':
                                debug = true;
                                break;
                            case 't':
                                test = true;
                                break;
                            case 'b':
                                benchmark = true;
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid flag: -" + arg.charAt(i));
                        }
                    }
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

        
        return new ParsedArgs(dayInt, partInt, debug, test, benchmark, help);
    }

    public record ParsedArgs(int day, int part, boolean debug, boolean test, boolean benchmark, boolean help) {
        public ParsedArgs(int day, int part) {
            this(day, part, false, false,false,false);
        }
    }

}
