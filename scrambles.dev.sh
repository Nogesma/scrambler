#!/usr/bin/env sh
echo "Job started: $(date)"
java -jar build/libs/scrambler.jar localhost 27017 test
echo "Job finished: $(date)"
