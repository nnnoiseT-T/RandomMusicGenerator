package com.musicgenerator;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a complete musical piece with melody, harmony, and rhythm
 * 表示完整的音乐作品，包含旋律、和声和节奏
 */
public class MusicalPiece {
    private List<Note> melody;
    private List<Chord> chords;
    private List<Note> bassLine;
    private int tempo;
    private int timeSignature;
    private int ticksPerBeat;
    private String title;
    private String composer;
    
    public MusicalPiece() {
        this.melody = new ArrayList<>();
        this.chords = new ArrayList<>();
        this.bassLine = new ArrayList<>();
        this.tempo = 120;
        this.timeSignature = 4;
        this.ticksPerBeat = 480;
        this.title = "AI Generated Music";
        this.composer = "Random Music Generator";
    }
    
    // Getters and setters
    public List<Note> getMelody() { return new ArrayList<>(melody); }
    public void setMelody(List<Note> melody) { this.melody = new ArrayList<>(melody); }
    
    public List<Chord> getChords() { return new ArrayList<>(chords); }
    public void setChords(List<Chord> chords) { this.chords = new ArrayList<>(chords); }
    
    public List<Note> getBassLine() { return new ArrayList<>(bassLine); }
    public void setBassLine(List<Note> bassLine) { this.bassLine = new ArrayList<>(bassLine); }
    
    public int getTempo() { return tempo; }
    public void setTempo(int tempo) { this.tempo = tempo; }
    
    public int getTimeSignature() { return timeSignature; }
    public void setTimeSignature(int timeSignature) { this.timeSignature = timeSignature; }
    
    public int getTicksPerBeat() { return ticksPerBeat; }
    public void setTicksPerBeat(int ticksPerBeat) { this.ticksPerBeat = ticksPerBeat; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getComposer() { return composer; }
    public void setComposer(String composer) { this.composer = composer; }
    
    /**
     * Get total duration of the piece in ticks
     * 获取作品总时长（以tick为单位）
     */
    public long getTotalDuration() {
        long maxDuration = 0;
        
        // Check melody duration
        for (Note note : melody) {
            long endTime = note.getStartTime() + note.getDuration();
            maxDuration = Math.max(maxDuration, endTime);
        }
        
        // Check chord duration
        for (Chord chord : chords) {
            long endTime = chord.getStartTime() + chord.getDuration();
            maxDuration = Math.max(maxDuration, endTime);
        }
        
        // Check bass line duration
        for (Note note : bassLine) {
            long endTime = note.getStartTime() + note.getDuration();
            maxDuration = Math.max(maxDuration, endTime);
        }
        
        return maxDuration;
    }
    
    /**
     * Get total duration in seconds
     * 获取总时长（以秒为单位）
     */
    public double getDurationInSeconds() {
        long totalTicks = getTotalDuration();
        double ticksPerSecond = (tempo * ticksPerBeat) / 60.0;
        return totalTicks / ticksPerSecond;
    }
    
    /**
     * Get piece statistics
     * 获取作品统计信息
     */
    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== Music Piece Statistics ===\n");
        stats.append("Title: ").append(title).append("\n");
        stats.append("Composer: ").append(composer).append("\n");
        stats.append("Tempo: ").append(tempo).append(" BPM\n");
        stats.append("Time Signature: ").append(timeSignature).append("/4\n");
        stats.append("Duration: ").append(String.format("%.2f", getDurationInSeconds())).append(" seconds\n");
        stats.append("Melody Notes: ").append(melody.size()).append("\n");
        stats.append("Chords: ").append(chords.size()).append("\n");
        stats.append("Bass Notes: ").append(bassLine.size()).append("\n");
        stats.append("Total Ticks: ").append(getTotalDuration()).append("\n");
        
        return stats.toString();
    }
    
    /**
     * Add a note to the melody
     * 向旋律添加音符
     */
    public void addMelodyNote(Note note) {
        melody.add(note);
    }
    
    /**
     * Add a chord to the piece
     * 向作品添加和弦
     */
    public void addChord(Chord chord) {
        chords.add(chord);
    }
    
    /**
     * Add a bass note
     * 添加低音音符
     */
    public void addBassNote(Note note) {
        bassLine.add(note);
    }
    
    /**
     * Clear all musical content
     * 清除所有音乐内容
     */
    public void clear() {
        melody.clear();
        chords.clear();
        bassLine.clear();
    }
    
    /**
     * Check if the piece has any musical content
     * 检查作品是否有音乐内容
     */
    public boolean isEmpty() {
        return melody.isEmpty() && chords.isEmpty() && bassLine.isEmpty();
    }
    
    /**
     * Get all notes from all tracks combined
     * 获取所有轨道的所有音符
     */
    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();
        allNotes.addAll(melody);
        allNotes.addAll(bassLine);
        
        // Add chord notes
        for (Chord chord : chords) {
            allNotes.addAll(chord.getNotes());
        }
        
        return allNotes;
    }
    
    @Override
    public String toString() {
        return String.format("MusicalPiece{title='%s', duration=%.2fs, melody=%d notes, chords=%d, bass=%d notes}", 
                           title, getDurationInSeconds(), melody.size(), chords.size(), bassLine.size());
    }
} 