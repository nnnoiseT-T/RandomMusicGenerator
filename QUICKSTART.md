# Quick Start Guide - Random Music Generator

## Get Started in 5 Minutes

Welcome to the Random Music Generator! This guide will help you create your first AI-generated music piece in just a few minutes.

## Prerequisites

- ✅ Java 11+ installed
- ✅ Maven 3.6+ installed
- ✅ Basic understanding of music concepts

## Quick Start Steps

### Step 1: Launch the Application
```bash
cd ~/Desktop/random-music-generator
./run-cli.sh
```

### Step 2: Generate Your First Music
1. **Select Option 1** - Generate Random Music
2. **Enter bars**: 4 (for a 4-bar piece)
3. **Enter time signature**: 4 (for 4/4 time)
4. **Wait for generation** - Watch the progress!

### Step 3: Find Your Music
Your MIDI file will be saved in the project directory with a timestamp:
```
generated_music_[timestamp].mid
```

## Music Generation Tips

### Scale Selection
- **Option 3** - Change Scale
- **Scale Types**:
  - 1: Random (surprise me!)
  - 2: Major (bright, happy)
  - 3: Minor (deep, emotional)
  - 4: Pentatonic (eastern, meditative)
  - 5: Blues (soulful, expressive)

### Parameter Adjustment
- **Option 2** - Customize Generation Parameters
- **Melody Complexity**: 0.0 (simple) to 1.0 (complex)
- **Harmony Complexity**: 0.0 (basic) to 1.0 (rich)
- **Rhythm Variation**: 0.0 (steady) to 1.0 (dynamic)

### Quick Melody Creation
- **Option 4** - Create Simple Melody
- Generates a basic C major scale melody
- Perfect for beginners!

## Using Your Generated Music

### Open in Music Software
- **GarageBand** (Mac) - Drag and drop the MIDI file
- **Logic Pro** - Import MIDI file
- **FL Studio** - Load MIDI file
- **Any DAW** - Most support MIDI import

### Edit and Customize
- Change instruments
- Adjust tempo
- Modify notes
- Add effects
- Export as audio

## Advanced Features

### Batch Generation
```bash
./demo.sh
```
This script generates multiple pieces with different scales automatically!

### Configuration
Edit `config.properties` to set default values:
```properties
# Default generation parameters
default.melody.complexity=0.7
default.harmony.complexity=0.6
default.rhythm.variation=0.5
default.tempo=120
```

### Custom Scales
Modify `MusicTheory.java` to add new scales:
```java
// Add your custom scale
public static int[] CUSTOM_SCALE = {60, 62, 64, 67, 69, 71, 74, 76};
```

## Best Practices

### For Beginners
1. Start with **Option 4** (Simple Melody)
2. Use **Option 3** to try different scales
3. Keep parameters low (0.3-0.5) initially
4. Generate short pieces (2-4 bars)

### For Intermediate Users
1. Experiment with **Option 2** parameter tuning
2. Try **Option 1** with different bar counts
3. Mix and match scales and parameters
4. Use the demo script for variety

### For Advanced Users
1. Modify the source code for custom algorithms
2. Add new chord progressions
3. Implement custom rhythm patterns
4. Create style-specific generators

## Troubleshooting

### Common Issues
- **"Java not found"**: Install Java 11+ and set JAVA_HOME
- **"Maven not found"**: Install Maven 3.6+
- **"Permission denied"**: Run `chmod +x *.sh`

### Performance Tips
- Use lower complexity for faster generation
- Generate shorter pieces for quick testing
- Close other applications for better performance

## Success Indicators

You'll know you're successful when:
- ✅ Application starts without errors
- ✅ Music generation completes
- ✅ MIDI file appears in directory
- ✅ File opens in your music software
- ✅ You can hear the generated music!

## Next Steps

After mastering the basics:
1. **Explore different scales** - Each has unique character
2. **Experiment with parameters** - Find your preferred style
3. **Generate longer pieces** - 8-16 bars for full compositions
4. **Try the demo script** - Discover variety automatically
5. **Customize the code** - Add your own musical ideas

## Happy Composing!

Remember: The best way to learn is to experiment! Try different combinations and see what beautiful music you can create.

**Start creating your AI music masterpiece now!**

---

**Need help? Check the main README.md for detailed documentation!** 