# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Advent of Code 2025 solution repository written in Java using Maven. The project uses a reflection-based architecture to dynamically load daily puzzle solvers.

## Build and Test Commands

```bash
# Compile the project
mvn compile

# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=AppTest

# Run the application (solve a specific day and part)
mvn exec:java --sun-misc-unsafe-memory-access=allow -Dexec.args="<day> <part>"
# Example: mvn exec:java -Dexec.args="1 1"
# Arguments can also use "day" and "part" prefixes: "day1 part1"

# Clean build artifacts
mvn clean

# Package the project
mvn package
```

## Architecture

### DaySolver Pattern

The project uses an abstract `DaySolver` base class with reflection-based instantiation:

- **DaySolver.forDay(int day)**: Uses reflection to dynamically load the solver class for a given day
  - Constructs class name as `it.vladastos.solutions.day{N}.Solver`
  - Example: day 1 → `it.vladastos.solutions.day1.Solver`

- **Abstract methods**: Each day's solver must extend `DaySolver` and override:
  - `solvePart1(String input)`: Returns the solution for part 1
  - `solvePart2(String input)`: Returns the solution for part 2

### Package Structure

```
it.vladastos/
├── App.java                           # Main entry point, argument parsing
├── daysolver/
│   └── DaySolver.java                 # Abstract base class for all solvers
├── solutions/
│   └── day{N}/
│       └── Solver.java                # Concrete solver for day N
└── resources/
    └── day{N}/                        # Input files for day N (if needed)
```

### Creating a New Day's Solution

1. Create package: `src/main/java/it/vladastos/solutions/day{N}/`
2. Create `Solver.java` extending `DaySolver`
3. Override `solvePart1()` and/or `solvePart2()`
4. The solver will be automatically discoverable via reflection

### Input Handling

Currently, the `DaySolver.solve()` method has placeholder input handling (empty string). Input loading mechanism is marked with TODO comments and needs to be implemented per the project's needs.

## Testing

The project uses JUnit 5 (Jupiter) for testing:
- Test classes should be in `src/test/java/it/vladastos/`
- JUnit 5 API and parameterized tests are available via `junit-bom`
