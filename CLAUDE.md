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
mvn exec:java -Dexec.args="<day> <part>"
# Example: mvn exec:java -Dexec.args="1 1"
# Arguments can also use "day" and "part" prefixes: "day1 part1"

# Quick run using the shell script
./run.sh <day> <part>

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
  - Automatically loads input from resources as `day{N}_input.txt`

- **Abstract methods**: Each day's solver must extend `DaySolver` and override:
  - `solvePart1()`: Returns the solution for part 1 (throws RuntimeException if not implemented)
  - `solvePart2()`: Returns the solution for part 2 (throws RuntimeException if not implemented)
  - Access input via `getInput()` method inherited from `DaySolver`

### Package Structure

```
src/main
├── java
│   └── it.vladastos/
│       ├── App.java                           # Main entry point, argument parsing
│       ├── daysolver/
│       │   └── DaySolver.java                 # Abstract base class for all solvers
│       ├── solutions/
│       │   └── day{N}/
│       │       └── Solver.java                # Concrete solver for day N
└── resources/
    └── day{N}_input.txt               # Input file for day N
```

### Creating a New Day's Solution

1. Create package: `src/main/java/it/vladastos/solutions/day{N}/`
2. Create `Solver.java` extending `DaySolver`
3. Override `solvePart1()` and/or `solvePart2()`
4. Place input file at `src/main/resources/day{N}_input.txt`
5. The solver will be automatically discoverable via reflection

### Input Handling

- Input files are automatically loaded from `src/main/resources/day{N}_input.txt`
- The `DaySolver` base class handles input loading via reflection in `initInput()`
- Access the input in solver implementations using `getInput()`
- Input is read as a single string (Scanner with delimiter `\\A`)

### Argument Parsing

The `App.main()` method accepts arguments with flexible formatting:
- Numeric only: `1 1` (day 1, part 1)
- Prefixed: `day1 part1` (automatically strips "day" and "part" prefixes)

## Testing

The project uses JUnit 5 (Jupiter) for testing:
- Test classes should be in `src/test/java/it/vladastos/`
- JUnit 5 API and parameterized tests are available via `junit-bom`
