package it.vladastos;

/**
 * Record to hold the parsed arguments
 * Values are validated in the parseArgs method, so they are guaranteed to be valid
 */
public record ParsedArgs(int day, int part) {

    private static final String DAY_PREFIX = "day";
    private static final String PART_PREFIX = "part";
    
    public static ParsedArgs parse (String[] args)  throws IllegalArgumentException {
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

        // Validate the arguments
        if (dayInt < 1 || dayInt > 25) {
            throw new IllegalArgumentException("Invalid day argument. Must be between 1 and 25: " + day);
        }
        if (partInt < 1 || partInt > 2) {
            throw new IllegalArgumentException("Invalid part argument. Must be between 1 and 2: " + part);
        }

        return new ParsedArgs(dayInt, partInt);
    }
}
