#!/bin/bash

# Advent of Code 2025 - Run script
# Usage: ./run.sh <day> <part>

function recompile {
    echo "Recompiling..."
    mvn clean --quiet
    mvn compile --quiet
}

function run {
    echo "Running..."
    mvn exec:java --quiet -Dexec.args="$day $part"
}

if [ $# -ne 2 ]; then
    echo "Usage: $0 <day> <part>"
    echo "Example: $0 1 1"
    exit 1
fi

day=$1
part=$2

recompile
run

