#!/bin/bash

echo "🎵 Random Music Generator - Demo Script 🎵"
echo "=========================================="

# Check if project is compiled
if [ ! -d "target/classes" ]; then
    echo "🔨 Compiling project first..."
    mvn clean compile
    if [ $? -ne 0 ]; then
        echo "❌ Compilation failed!"
        exit 1
    fi
fi

echo "✅ Project ready for demo generation!"

# Create demo directory
DEMO_DIR="demo_output_$(date +%s)"
mkdir -p "$DEMO_DIR"
echo "📁 Created demo directory: $DEMO_DIR"

echo ""
echo "🎼 Generating demo music pieces with different scales..."

# Generate Major scale piece
echo "🎵 Generating Major scale piece..."
mvn exec:java -Dexec.mainClass="com.musicgenerator.MusicGeneratorApp" -Dexec.args="demo_major" > /dev/null 2>&1
if [ -f "generated_music_*.mid" ]; then
    mv generated_music_*.mid "$DEMO_DIR/major_scale_demo.mid"
    echo "✅ Major scale piece generated"
fi

# Generate Minor scale piece
echo "🎵 Generating Minor scale piece..."
mvn exec:java -Dexec.mainClass="com.musicgenerator.MusicGeneratorApp" -Dexec.args="demo_minor" > /dev/null 2>&1
if [ -f "generated_music_*.mid" ]; then
    mv generated_music_*.mid "$DEMO_DIR/minor_scale_demo.mid"
    echo "✅ Minor scale piece generated"
fi

# Generate Pentatonic scale piece
echo "🎵 Generating Pentatonic scale piece..."
mvn exec:java -Dexec.mainClass="com.musicgenerator.MusicGeneratorApp" -Dexec.args="demo_pentatonic" > /dev/null 2>&1
if [ -f "generated_music_*.mid" ]; then
    mv generated_music_*.mid "$DEMO_DIR/pentatonic_scale_demo.mid"
    echo "✅ Pentatonic scale piece generated"
fi

# Generate Blues scale piece
echo "🎵 Generating Blues scale piece..."
mvn exec:java -Dexec.mainClass="com.musicgenerator.MusicGeneratorApp" -Dexec.args="demo_blues" > /dev/null 2>&1
if [ -f "generated_music_*.mid" ]; then
    mv generated_music_*.mid "$DEMO_DIR/blues_scale_demo.mid"
    echo "✅ Blues scale piece generated"
fi

echo ""
echo "🎉 Demo generation completed!"
echo "📁 Check the '$DEMO_DIR' directory for your demo pieces:"
ls -la "$DEMO_DIR"

echo ""
echo "🎵 You can now open these MIDI files in your music software to hear the different musical styles!"
echo "🎼 Each piece demonstrates the unique character of different musical scales." 