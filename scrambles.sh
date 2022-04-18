#!/usr/bin/env sh
echo "Job started: $(date)"
java -jar /scrambler.jar mongod 27017 cube
echo "Job finished: $(date)"
