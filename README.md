# Random Music Generator - AI Style Composer

## What is this?

A Java-based random music generator that creates AI-style music using music theory, random algorithms, and MIDI technology.

Simply put, it's like having a computer help you write songs! 🎵

## Features

- **Melody Generation**: Creates harmonious melodies based on scale theory
- **Chord Progressions**: Supports major, minor, and diminished triads
- **Bass Lines**: Auto-generates bass parts
- **Multiple Scales**: Major, minor, pentatonic, and blues scales
- **MIDI Export**: Standard format compatible with all music software

## Quick Start

### Requirements
- Java 11+
- Maven 3.6+

### Run
```bash
git clone <repository-url>
cd random-music-generator
./run-cli.sh
```

## How to Use

After starting, you'll see a menu with options to:
1. Generate random music
2. Adjust parameters (melody, harmony, rhythm complexity)
3. Change scales
4. Create simple melodies
5. View current settings

## Project Structure

```
random-music-generator/
├── src/main/java/com/musicgenerator/
│   ├── Note.java              # handles individual notes
│   ├── Chord.java             # creates and manages chords
│   ├── MusicTheory.java       # music theory helpers
│   ├── MusicGenerator.java    # the main music generator
│   ├── MusicalPiece.java      # holds the complete music
│   ├── MidiExporter.java      # exports to MIDI files
│   └── MusicGeneratorApp.java # command line interface
├── pom.xml, run-cli.sh, demo.sh, config.properties
└── README.md
```

## Status

✅ **Core music generation working**
✅ **Command line interface stable**
✅ **No external dependencies**
✅ **Cross-platform compatible**

## Contributing

Feel free to submit issues and pull requests!

## License

MIT License

---

**Enjoy creating AI music!** 🎶

*Need help? Check QUICKSTART.md* 