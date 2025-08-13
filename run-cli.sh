#!/bin/bash

echo "🎵 Random Music Generator - Command Line Version 🎵"
echo "=================================================="

# Check if Java and Maven are installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

echo "✅ Java and Maven are installed"
echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version | head -n 1)"

echo ""
echo "🔨 Building project..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Project built successfully!"
else
    echo "❌ Build failed!"
    exit 1
fi

echo ""
echo "🚀 Starting command line application..."
echo "This version works reliably and has all the same features!"

# Start the application using Maven exec plugin
mvn exec:java -Dexec.mainClass="com.musicgenerator.MusicGeneratorApp"

echo ""
echo "🎉 Application closed."
echo "🎵 Thank you for using Random Music Generator!" 