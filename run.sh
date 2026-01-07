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
    mvn exec:java --quiet -Dexec.args="$*"
}

function check_args {
    if [ $# -lt 2 ]; then
        print_usage
        exit 1
    fi
}



# Main

check_args $@

recompile
run "$@"