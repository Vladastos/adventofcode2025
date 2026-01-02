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

- **DaySolver.fromParsedArgs(ParsedArgs args)**: Factory method that uses reflection to dynamically load the solver class for a given day
  - Constructs class name as `it.vladastos.solutions.day{N}.Solver` (where {N} is the day number)
  - Example: day 1 → `it.vladastos.solutions.day1.Solver`
  - Automatically loads input from resources as `day{N}_input.txt`
  - Returns an initialized DaySolver instance ready to solve
  - Throws `SolverNotFoundException` if the solver class cannot be found or instantiated
  - Throws `InputFileException` if the input file cannot be loaded

- **solve()**: Main solving method that dispatches to the appropriate part solver
  - Calls `solvePart1()` or `solvePart2()` based on the part specified in ParsedArgs
  - Returns a String containing the solution

- **Abstract methods**: Each day's solver must extend `DaySolver` and override:
  - `solvePart1()`: Returns the solution for part 1 as a String (throws `UnimplementedException` if not overridden)
  - `solvePart2()`: Returns the solution for part 2 as a String (throws `UnimplementedException` if not overridden)
  - Access input via `getInput()` method inherited from `DaySolver`

### Package Structure

```
src/main
├── java
│   └── it.vladastos/
│       ├── App.java                           # Main entry point, argument parsing
│       ├── DaySolver.java                     # Abstract base class for all solvers
│       ├── ParsedArgs.java                    # Argument parsing class
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

- Argument parsing is handled by `ParsedArgs` class
- The `App` class uses `ParsedArgs` to parse command-line arguments