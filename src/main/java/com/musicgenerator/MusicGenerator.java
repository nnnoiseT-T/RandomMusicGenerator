package com.musicgenerator;

import java.util.*;
import java.util.Random;

/**
 * Core music generator that creates AI-style melodies and harmonies
 * 核心音乐生成器，创建AI风格的旋律和和声
 */
public class MusicGenerator {
    
    private Random random;
    private MusicTheory.ScaleType currentScaleType;
    private int rootNote;
    private int[] currentScale;
    private int tempo;
    private int ticksPerBeat;
    
    // Musical parameters for generation
    // 音乐生成参数
    private double melodyComplexity;      // 0.0 to 1.0
    private double harmonyComplexity;     // 0.0 to 1.0
    private double rhythmVariety;         // 0.0 to 1.0
    
    public MusicGenerator() {
        this.random = new Random();
        this.tempo = 120;
        this.ticksPerBeat = 480;
        this.melodyComplexity = 0.7;
        this.harmonyComplexity = 0.6;
        this.rhythmVariety = 0.5;
        
        // Initialize with random scale
        // 用随机音阶初始化
        initializeRandomScale();
    }
    
    /**
     * Initialize with a random scale and root note
     * 用随机音阶和根音初始化
     */
    private void initializeRandomScale() {
        this.currentScaleType = MusicTheory.getRandomScaleType();
        this.rootNote = MusicTheory.getRandomRootNote();
        this.currentScale = MusicTheory.getScale(currentScaleType, rootNote);
    }
    
    /**
     * Set generation parameters
     * 设置生成参数
     */
    public void setParameters(double melodyComplexity, double harmonyComplexity, double rhythmVariety) {
        this.melodyComplexity = Math.max(0.0, Math.min(1.0, melodyComplexity));
        this.harmonyComplexity = Math.max(0.0, Math.min(1.0, harmonyComplexity));
        this.rhythmVariety = Math.max(0.0, Math.min(1.0, rhythmVariety));
    }
    
    /**
     * Set tempo and time signature
     * 设置速度和拍号
     */
    public void setTempo(int tempo, int ticksPerBeat) {
        this.tempo = tempo;
        this.ticksPerBeat = ticksPerBeat;
    }
    
    /**
     * Generate a complete musical piece
     * 生成完整的音乐作品
     */
    public MusicalPiece generatePiece(int measures, int timeSignature) {
        MusicalPiece piece = new MusicalPiece();
        piece.setTempo(tempo);
        piece.setTimeSignature(timeSignature);
        piece.setTicksPerBeat(ticksPerBeat);
        
        // Generate chord progression
        // 生成和弦进行
        int[] chordProgression = MusicTheory.getRandomChordProgression();
        List<Chord> chords = generateChordProgression(chordProgression, measures, timeSignature);
        piece.setChords(chords);
        
        // Generate melody based on chords
        // 基于和弦生成旋律
        List<Note> melody = generateMelody(chords, measures, timeSignature);
        piece.setMelody(melody);
        
        // Generate bass line
        // 生成低音线
        List<Note> bassLine = generateBassLine(chords, measures, timeSignature);
        piece.setBassLine(bassLine);
        
        return piece;
    }
    
    /**
     * Generate chord progression
     * 生成和弦进行
     */
    private List<Chord> generateChordProgression(int[] progression, int measures, int timeSignature) {
        List<Chord> chords = new ArrayList<>();
        int beatsPerMeasure = timeSignature;
        int ticksPerMeasure = beatsPerMeasure * ticksPerBeat;
        
        for (int measure = 0; measure < measures; measure++) {
            for (int beat = 0; beat < beatsPerMeasure; beat++) {
                int chordIndex = (measure * beatsPerMeasure + beat) % progression.length;
                int chordRoot = currentScale[progression[chordIndex]];
                
                // Randomly choose chord type based on harmony complexity
                // 基于和声复杂度随机选择和弦类型
                Chord chord;
                if (random.nextDouble() < harmonyComplexity) {
                    if (random.nextBoolean()) {
                        chord = Chord.createMajorChord(chordRoot, ticksPerBeat, 
                                (measure * ticksPerMeasure) + (beat * ticksPerBeat));
                    } else {
                        chord = Chord.createMinorChord(chordRoot, ticksPerBeat, 
                                (measure * ticksPerMeasure) + (beat * ticksPerBeat));
                    }
                } else {
                    chord = Chord.createMajorChord(chordRoot, ticksPerBeat, 
                            (measure * ticksPerMeasure) + (beat * ticksPerBeat));
                }
                
                chords.add(chord);
            }
        }
        
        return chords;
    }
    
    /**
     * Generate melody based on chord progression
     * 基于和弦进行生成旋律
     */
    private List<Note> generateMelody(List<Chord> chords, int measures, int timeSignature) {
        List<Note> melody = new ArrayList<>();
        int ticksPerMeasure = timeSignature * ticksPerBeat;
        
        for (int measure = 0; measure < measures; measure++) {
            long measureStartTime = measure * ticksPerMeasure;
            
            // Generate melody notes for this measure
            // 为这一小节生成旋律音符
            List<Note> measureMelody = generateMeasureMelody(measureStartTime, timeSignature);
            melody.addAll(measureMelody);
        }
        
        return melody;
    }
    
    /**
     * Generate melody for a single measure
     * 为单个小节生成旋律
     */
    private List<Note> generateMeasureMelody(long measureStartTime, int timeSignature) {
        List<Note> measureMelody = new ArrayList<>();
        int ticksPerBeat = this.ticksPerBeat;
        
        // Determine rhythm pattern based on rhythm variety
        // 基于节奏变化确定节奏模式
        List<Integer> rhythmPattern = generateRhythmPattern(timeSignature);
        
        for (int beat = 0; beat < timeSignature; beat++) {
            long beatStartTime = measureStartTime + (beat * ticksPerBeat);
            int beatDuration = rhythmPattern.get(beat);
            
            if (beatDuration > 0) {
                // Generate note for this beat
                // 为这一拍生成音符
                Note note = generateMelodyNote(beatStartTime, beatDuration);
                if (note != null) {
                    measureMelody.add(note);
                }
            }
        }
        
        return measureMelody;
    }
    
    /**
     * Generate rhythm pattern for a measure
     * 为小节生成节奏模式
     */
    private List<Integer> generateRhythmPattern(int timeSignature) {
        List<Integer> pattern = new ArrayList<>();
        
        for (int beat = 0; beat < timeSignature; beat++) {
            if (random.nextDouble() < rhythmVariety) {
                // Add variety: shorter notes, rests, syncopation
                // 添加变化：短音符、休止符、切分音
                double rand = random.nextDouble();
                if (rand < 0.3) {
                    pattern.add(ticksPerBeat / 2);  // Half note
                } else if (rand < 0.6) {
                    pattern.add(ticksPerBeat / 4);  // Quarter note
                } else {
                    pattern.add(ticksPerBeat);      // Whole note
                }
            } else {
                pattern.add(ticksPerBeat);  // Standard beat
            }
        }
        
        return pattern;
    }
    
    /**
     * Generate a single melody note
     * 生成单个旋律音符
     */
    private Note generateMelodyNote(long startTime, int duration) {
        // Choose note from current scale
        // 从当前音阶中选择音符
        int pitch = currentScale[random.nextInt(currentScale.length)];
        
        // Add some octave variation based on melody complexity
        // 基于旋律复杂度添加八度变化
        if (random.nextDouble() < melodyComplexity) {
            int octaveShift = random.nextInt(3) - 1; // -1, 0, or 1
            pitch += octaveShift * 12;
        }
        
        // Ensure pitch is within MIDI range (21-108)
        // 确保音高在MIDI范围内（21-108）
        pitch = Math.max(21, Math.min(108, pitch));
        
        // Generate velocity based on complexity
        // 基于复杂度生成力度
        int velocity = 80 + (int)(melodyComplexity * 40);
        
        return new Note(pitch, duration, velocity, startTime);
    }
    
    /**
     * Generate bass line based on chord roots
     * 基于和弦根音生成低音线
     */
    private List<Note> generateBassLine(List<Chord> chords, int measures, int timeSignature) {
        List<Note> bassLine = new ArrayList<>();
        
        for (Chord chord : chords) {
            // Get root note of chord
            // 获取和弦的根音
            int rootPitch = chord.getNotes().get(0).getPitch();
            
            // Lower by one or two octaves for bass
            // 降低一两个八度作为低音
            int bassPitch = rootPitch - 12;
            if (random.nextDouble() < 0.3) {
                bassPitch -= 12; // Lower octave
            }
            
            // Ensure pitch is within bass range
            // 确保音高在低音范围内
            bassPitch = Math.max(21, Math.min(60, bassPitch));
            
            Note bassNote = new Note(bassPitch, chord.getDuration(), 90, chord.getStartTime());
            bassLine.add(bassNote);
        }
        
        return bassLine;
    }
    
    /**
     * Get current scale information
     * 获取当前音阶信息
     */
    public String getScaleInfo() {
        return String.format("Scale: %s, Root: %s%d", 
                           currentScaleType.toString(),
                           new Note(rootNote, 0, 0, 0).getNoteName(),
                           new Note(rootNote, 0, 0, 0).getOctave());
    }
    
    /**
     * Change to a new random scale
     * 改变为新的随机音阶
     */
    public void changeScale() {
        initializeRandomScale();
    }
    
    /**
     * Set specific scale and root note
     * 设置特定音阶和根音
     */
    public void setScale(MusicTheory.ScaleType scaleType, int rootNote) {
        this.currentScaleType = scaleType;
        this.rootNote = rootNote;
        this.currentScale = MusicTheory.getScale(scaleType, rootNote);
    }
} 