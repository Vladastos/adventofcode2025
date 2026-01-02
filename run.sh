#!/bin/bash
# Advent of Code 2025 - Run script
# Usage: ./run.sh <day> <part>

set -e  # Exit on error
set -u  # Exit on undefined variable
set -o pipefail  # Exit on pipe failures

function print_usage {
    echo "Usage: $0 <day> <part>"
    echo "Example: $0 1 1"
}
function recompile {
    echo "Recompiling..."
    mvn compile --quiet
}

function run {
    echo "Running..."
    mvn exec:java --quiet -Dexec.args="$day $part"
}
function check_input_file {
    if [ ! -f "src/main/resources/day${day}_input.txt" ]; then
        # Try to download the input file
        echo "Input file for day $day not found. Trying to download..."
        ./download-inputs.sh $day
        if [ ! -f "src/main/resources/day${day}_input.txt" ]; then
            echo "Input file for day $day still not found. Check if you have set the .aoc_cookie file."
            exit 1
        fi
    fi
}
function check_args {
    if [ $# -ne 2 ]; then
        print_usage
        exit 1
    fi

    if [ $1 -lt 1 ] || [ $1 -gt 25 ]; then
        echo "Day must be between 1 and 25"
        print_usage
        exit 1
    fi

    if [ $2 -lt 1 ] || [ $2 -gt 2 ]; then
        echo "Part must be 1 or 2"
        print_usage
        exit 1
    fi
}



# Main

check_args $@
day=$1
part=$2

check_input_file
recompile
run