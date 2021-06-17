#!/bin/bash

cat example.in | env PRINT_INPUT=1 java -classpath \
	./build/libs/RPNCalculator-1.0-SNAPSHOT.jar \
	com.demo.calculator.tool.CommandLineTool
