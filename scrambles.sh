#!/usr/bin/env sh
echo "Job started: $(date)"
java -jar /scrambler.jar localhost 27017 test
echo "Job finished: $(date)"
