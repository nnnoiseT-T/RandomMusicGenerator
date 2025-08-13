package com.musicgenerator;

/**
 * Represents a musical note with pitch, duration, and velocity
 * 表示一个音符，包含音高、时长和力度
 */
public class Note {
    private int pitch;        // MIDI pitch (0-127)
    private int duration;     // Duration in ticks
    private int velocity;     // Note velocity (0-127)
    private long startTime;   // Start time in ticks
    
    public Note(int pitch, int duration, int velocity, long startTime) {
        this.pitch = pitch;
        this.duration = duration;
        this.velocity = velocity;
        this.startTime = startTime;
    }
    
    // Getters
    public int getPitch() { return pitch; }
    public int getDuration() { return duration; }
    public int getVelocity() { return velocity; }
    public long getStartTime() { return startTime; }
    
    // Setters
    public void setPitch(int pitch) { this.pitch = pitch; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setVelocity(int velocity) { this.velocity = velocity; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    
    /**
     * Get the note name (e.g., "C", "D#", "F")
     * 获取音符名称（如 "C", "D#", "F"）
     */
    public String getNoteName() {
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        return noteNames[pitch % 12];
    }
    
    /**
     * Get the octave number
     * 获取八度数字
     */
    public int getOctave() {
        return (pitch / 12) - 1;
    }
    
    @Override
    public String toString() {
        return String.format("Note{pitch=%d, note=%s%d, duration=%d, velocity=%d, startTime=%d}", 
                           pitch, getNoteName(), getOctave(), duration, velocity, startTime);
    }
} 