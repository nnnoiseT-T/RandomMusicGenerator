package com.musicgenerator;

import java.util.Scanner;
import java.util.Random;

/**
 * Main application class for the Random Music Generator
 * 随机音乐生成器的主应用程序类
 */
public class MusicGeneratorApp {
    
    private static MusicGenerator generator;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        System.out.println("🎵 Random Music Generator - AI Style Composer 🎵");
        System.out.println("================================================");
        
        // Initialize components
        generator = new MusicGenerator();
        scanner = new Scanner(System.in);
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Please select an option (1-6): ");
            
            switch (choice) {
                case 1:
                    generateRandomMusic();
                    break;
                case 2:
                    customizeParameters();
                    break;
                case 3:
                    changeScale();
                    break;
                case 4:
                    createSimpleMelody();
                    break;
                case 5:
                    showScaleInfo();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Random Music Generator! 👋");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Generate Random Music");
        System.out.println("2. Customize Generation Parameters");
        System.out.println("3. Change Scale");
        System.out.println("4. Create Simple Melody");
        System.out.println("5. Display Current Scale Information");
        System.out.println("6. Exit");
        System.out.println("================");
    }
    
    /**
     * Generate random music with current settings
     */
    private static void generateRandomMusic() {
        System.out.println("\n=== Generate Random Music ===");
        
        int measures = getIntInput("Please enter number of bars (4-16): ");
        measures = Math.max(4, Math.min(16, measures));
        
        int timeSignature = getIntInput("Please enter time signature (3-6): ");
        timeSignature = Math.max(3, Math.min(6, timeSignature));
        
        System.out.println("Generating music...");
        System.out.println("Current settings: " + generator.getScaleInfo());
        
        try {
            MusicalPiece piece = generator.generatePiece(measures, timeSignature);
            
            System.out.println("\nMusic generation completed!");
            System.out.println(piece.getStatistics());
            
            // Export to MIDI
            // 导出为MIDI
            String filename = "generated_music_" + System.currentTimeMillis() + ".mid";
            MidiExporter.exportToMidi(piece, filename);
            
            System.out.println("MIDI file saved as: " + filename);
            
        } catch (Exception e) {
            System.err.println("Error generating music: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Customize generation parameters
     */
    private static void customizeParameters() {
        System.out.println("\n=== Customize Generation Parameters ===");
        System.out.println("All parameter ranges: 0.0 (simple) to 1.0 (complex)");
        
        double melodyComplexity = getDoubleInput("Melody complexity (0.0-1.0): ");
        double harmonyComplexity = getDoubleInput("Harmony complexity (0.0-1.0): ");
        double rhythmVariety = getDoubleInput("Rhythm variation (0.0-1.0): ");
        
        generator.setParameters(melodyComplexity, harmonyComplexity, rhythmVariety);
        
        System.out.println("Parameters set successfully!");
        System.out.println("Melody complexity: " + melodyComplexity);
        System.out.println("Harmony complexity: " + harmonyComplexity);
        System.out.println("Rhythm variation: " + rhythmVariety);
    }
    
    /**
     * Change the current scale
     */
    private static void changeScale() {
        System.out.println("\n=== Change Scale ===");
        System.out.println("1. Random Scale");
        System.out.println("2. Major Scale");
        System.out.println("3. Minor Scale");
        System.out.println("4. Pentatonic Scale");
        System.out.println("5. Blues Scale");
        
        int choice = getIntInput("Please select scale type (1-5): ");
        
        if (choice == 1) {
            generator.changeScale();
            System.out.println("Switched to random scale");
        } else if (choice >= 2 && choice <= 5) {
            MusicTheory.ScaleType[] types = MusicTheory.ScaleType.values();
            MusicTheory.ScaleType selectedType = types[choice - 2];
            
            int rootNote = getIntInput("Please enter root note (60-84, C4-C6): ");
            rootNote = Math.max(60, Math.min(84, rootNote));
            
            generator.setScale(selectedType, rootNote);
            System.out.println("Scale set to: " + selectedType + ", Root note: " + 
                             new Note(rootNote, 0, 0, 0).getNoteName() + 
                             new Note(rootNote, 0, 0, 0).getOctave());
        } else {
            System.out.println("Invalid choice");
        }
        
        System.out.println("Current scale: " + generator.getScaleInfo());
    }
    
    /**
     * Create a simple melody
     */
    private static void createSimpleMelody() {
        System.out.println("\n=== Create Simple Melody ===");
        
        // Create a simple C major scale melody
        int[] notes = {60, 62, 64, 65, 67, 69, 71, 72, 71, 69, 67, 65, 64, 62, 60}; // C major scale
        int[] durations = {1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2}; // Quarter notes
        
        try {
            String filename = "simple_melody_" + System.currentTimeMillis() + ".mid";
            MidiExporter.createSimpleMidi(filename, notes, durations);
            
            System.out.println("Simple melody created: " + filename);
            System.out.println("Notes: C D E F G A B C B A G F E D C");
            System.out.println("Rhythm: Quarter notes, last two notes are half notes");
            
        } catch (Exception e) {
            System.err.println("Error creating simple melody: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Show current scale information
     */
    private static void showScaleInfo() {
        System.out.println("\n=== Current Scale Information ===");
        System.out.println(generator.getScaleInfo());
        
        // Show available instruments
        System.out.println("\nAvailable MIDI instruments (first 20):");
        String[] instruments = MidiExporter.getAvailableInstruments();
        for (int i = 0; i < Math.min(20, instruments.length); i++) {
            System.out.println((i + 1) + ". " + instruments[i]);
        }
        System.out.println("... More instruments available");
    }
    
    /**
     * Get integer input from user
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    /**
     * Get double input from user
     */
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    /**
     * Demonstrate the music generator with examples
     */
    public static void demonstrateGenerator() {
        System.out.println("\n=== Demo Mode ===");
        
        // Create different types of music
        String[] scaleTypes = {"Major", "Minor", "Pentatonic", "Blues"};
        
        for (String scaleType : scaleTypes) {
            try {
                System.out.println("Generating " + scaleType + " scale music...");
                
                // Set scale type
                MusicTheory.ScaleType type = MusicTheory.ScaleType.valueOf(scaleType.toUpperCase());
                generator.setScale(type, 60); // C4
                
                // Generate piece
                MusicalPiece piece = generator.generatePiece(4, 4);
                
                // Export
                String filename = "demo_" + scaleType.toLowerCase() + ".mid";
                MidiExporter.exportToMidi(piece, filename);
                
                System.out.println("✓ " + scaleType + " music generated: " + filename);
                
            } catch (Exception e) {
                System.err.println("✗ Error generating " + scaleType + " music: " + e.getMessage());
            }
        }
        
        System.out.println("\nDemo completed! All MIDI files have been generated.");
    }
} 