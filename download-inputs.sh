#!/bin/bash
set -e  # Exit on error
set -u  # Exit on undefined variable
set -o pipefail  # Exit on pipe failures

function print_usage {
    echo "Usage: $0 <day|all>"
    echo "Example: $0 1"
    echo "         $0 all"
}

function check_args {
    if [ $# -ne 1 ]; then
        print_usage
        exit 1
    fi
}
function check_cookie_file {
    if [ ! -f ".aoc_cookie" ]; then
        echo "Cookie file not found. Please set the .aoc_cookie file."
        exit 1
    fi
    if [ ! -s ".aoc_cookie" ]; then
        echo "Cookie file is empty. Please set the .aoc_cookie file."
        exit 1
    fi
}


function check_dependencies {
    if ! command -v wget >/dev/null 2>&1; then
        echo "wget is not installed. Please install wget."
        exit 1
    fi

    if ! command -v mvn >/dev/null 2>&1; then
        echo "mvn is not installed. Please install Maven."
        exit 1
    fi
}

function download_input {
    day=$1
    echo "Downloading input for day $day..."
    mkdir -p "src/main/resources/day${day}"
    wget --quiet -O "src/main/resources/day${day}/input.txt" "https://adventofcode.com/2025/day/$day/input" --header "Cookie: session=$(cat .aoc_cookie)"
}

function download_all {
    for i in $(seq 1 $NUM_DAYS); do
        download_input $i
    done

    echo "All inputs downloaded."
}

# Main

check_args $@
check_cookie_file
check_dependencies
NUM_DAYS=12

if [ $1 = "all" ]; then
    download_all
    exit 0
fi

download_input $1


